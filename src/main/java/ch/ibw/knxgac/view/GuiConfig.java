package ch.ibw.knxgac.view;

import ch.ibw.knxgac.control.Controller;
import ch.ibw.knxgac.control.StringChecker;
import ch.ibw.knxgac.model.Configuration;
import ch.ibw.knxgac.model.Servertyp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GuiConfig {
    private Stage stage;
    private Controller controller;

    public GuiConfig(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public GridPane getConfigurationGrid(Configuration configuration, TabPane tabPane) {
        // Create GridPane to locate the Form-Fields
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);   // Margin
        grid.setVgap(10);   // Margin
        grid.setPadding(new Insets(15,15,15,15));   // Padding


        int y = 0;
        int x = 1;
        FieldHelper fieldHelper = new FieldHelper();

        // Add the Title-Lable
        y++;
        grid.add(fieldHelper.getLable("Datenbankkonfiguration", "Tahoma", 14, FontWeight.BOLD), x,y);




        // Setting up the ComboBox for the DB Servertyp
        y++;
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for(Servertyp s : Servertyp.values()) {
            items.add(new ComboBoxItem(s.ordinal(), s.name()));
        }
        ComboBox<ComboBoxItem> cBservertyp = new ComboBox<>();
        cBservertyp.getItems().addAll(items);
        // Add the Enum Servertyp Values to the ComboBox
        // if no choosen servertyp in config, select the first available typ
        if(configuration.getDbServertyp() == null) {
            // no servertype choosen -> select the first
            cBservertyp.getSelectionModel().selectFirst();
        } else {
            // choose the servertyp saved in configuration
            cBservertyp.getSelectionModel().select(configuration.getDbServertyp().ordinal());
        }
        grid.add(cBservertyp, x+1, y);

        // add a lable and a TextField for Formfield DB Server
        y++;
        grid.add(fieldHelper.getLable("Server"), x,y);
        TextField tfServer = fieldHelper.getTextField(configuration.getDbServer());
        grid.add(tfServer, x+1, y);

        // add a lable and a TextField for Formfield DB Server Port
        y++;
        grid.add(fieldHelper.getLable("Server Port"), x,y);
        TextField tfServerPort = fieldHelper.getTextField(String.valueOf(configuration.getDbServerPort()));
        tfServerPort.setTooltip(new Tooltip("Bitte geben Sie für den Port nur Zahlen ein.\nDer MySQL Standardport ist 3306."));
        grid.add(tfServerPort, x+1, y);

        // add a lable and a TextField for Formfield DB Name
        y++;
        grid.add(fieldHelper.getLable("Datenbank-Name"), x,y);
        TextField tfDbName = fieldHelper.getTextField(configuration.getDbName());
        tfDbName.setTooltip(new Tooltip("Bitte geben Sie für den Datenbank-Namen nur Buchstaben ein. \nZum Beispiel \"KNXGAC\""));
        grid.add(tfDbName, x+1, y);
        y++;

        // add a lable and a TextField for Formfield DB User
        grid.add(fieldHelper.getLable("User"), x,y);
        TextField tfUser = fieldHelper.getTextField(configuration.getDbUsername());
        grid.add(tfUser, x+1, y);
        y++;

        // add a lable and a PasswordField for Formfield DB Password
        grid.add(fieldHelper.getLable("Passwort"), x,y);
        TextField tfPassword = fieldHelper.getPasswordField(configuration.getDbPassword());
        grid.add(tfPassword, x+1, y);

        y++;
        // add a title as a lable for the output
        y++;
        grid.add(fieldHelper.getLable("Output", "Tahoma", 14, FontWeight.BOLD), x,y);

        // add a lable and a TextField for Formfield CSV Outputpath
        y++;
        grid.add(fieldHelper.getLable("CSV Ausgabepfad"), x,y);
        TextField csvpath = fieldHelper.getTextField(configuration.getCsvOutputpath(), "csvpath");
        grid.add(csvpath, x+1, y);

        // Create a HBox where the DirectoryChooser is placed in
        HBox hbBtnDir = new HBox(10);
        hbBtnDir.setAlignment(Pos.BOTTOM_RIGHT);
        // add the DirectoryChooser to the HBox
        hbBtnDir.getChildren().addAll(fieldHelper.getDirectoryChooser(this.stage, "Verzeichnis wählen", csvpath));
        grid.add(hbBtnDir, x+2, y);

        y++;
        y++;
        y++;
        // Create the Save-Button
        Button btnSave = new Button();
        btnSave.setText("speichern");
        // Add the EventHandler to the Save-Button
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean errorOccurred = false;
                // create a new configuration-object to save the formdata in it
                Configuration config = new Configuration();
                // get the servertyp-object of the servertyp-field-string out of the form
                config.setDbServertyp(Servertyp.valueOf(cBservertyp.getSelectionModel().getSelectedItem().getName()));
                // set data from fx-form to the configuration form
                config.setDbServer(tfServer.getText());
                if(StringChecker.checkStringOnlyNumbers(tfServerPort.getText())) {
                    config.setDbServerPort(Integer.parseInt(tfServerPort.getText()));
                } else {
                    errorOccurred = true;
                    wrongInput("Server Port", "Bitte korrigieren Sie die Port-Angabe. \nEs sind nur Zahlen erlaubt.").showAndWait();
                }
                if(StringChecker.checkStringOnlyLettersNoUmlaute(tfDbName.getText())) {
                    config.setDbName(tfDbName.getText());
                } else {
                    errorOccurred = true;
                    wrongInput("Datenbank Name", "Prüfen Sie den Dankbanknamen. \nEs sind nur Buchstaben erlaubt - keine Umlaute.").showAndWait();
                }
                config.setDbUsername(tfUser.getText());
                config.setDbPassword(tfPassword.getText());
                config.setCsvOutputpath(csvpath.getText());
                if(!errorOccurred) {
                    try {
                        // save the config
                        controller.saveConfiguration(config);
                        // load the config out of the file again
                        Configuration newConfiguration = controller.getConfiguration();
                        // check config against database
                        if (controller.checkConfiguration(newConfiguration)) {
                            new Dialog().getInformation(
                                    "Information",
                                    "Erfolgreich gespeichert",
                                    "Die Konfiguration wurde erfolgreich gespeichert."
                            ).showAndWait();
                            KnxGacApplication.configuration = newConfiguration;
                            KnxGacApplication.configGui = false;
                            for (Tab tab : tabPane.getTabs()) {
                                tab.setDisable(false);
                            }
                            tabPane.getSelectionModel().selectFirst();
                        } else {
                            new Dialog().getInformation(
                                    "Information",
                                    "Speichern nicht möglich",
                                    "Bitte füllen Sie die Felder korrekt aus."
                            ).showAndWait();
                        }
                    } catch (SQLException exception) {
                        if (exception.getErrorCode() == 1007) {
                            // datenbank existiert schon, deshalb konnte nicht geschrieben werden - timingproblem...
                            new Dialog().getInformation(
                                    "Information",
                                    "Die Datenbank wurde erstellt",
                                    "Bitte klicken Sie nochmals speichern \num die notwendigen Einstellungen zu speichern."
                            ).showAndWait();
                        } else {
                            // deactivate all tabs except the selected one
                            Tab actualTab = tabPane.getSelectionModel().getSelectedItem();
                            for (Tab tab : tabPane.getTabs()) {
                                if (!tab.equals(actualTab)) {
                                    tab.setDisable(true);
                                }
                            }
                            // alert an error because of the SQLException
                            new Dialog().getException(
                                    "SQL Exception",
                                    "Fehler bei der Datenbankverbindung",
                                    "Bitte prüfen Sie ihre Datenbankverbindungsangaben.\n" +
                                            "Es konnte keine Verbindung zur Datenbank aufgebaut werden.",
                                    exception

                            ).showAndWait();
                        }
                    } catch (IOException exception) {
                        // alert an error because of the IOException
                        new Dialog().getException(
                                "IO Exception",
                                "Fehler beim Speichern",
                                "Die Konfiguration konnte nicht gespeichert werden.\n" +
                                        "Löschen Sie die Datei configuration.txt und \n" +
                                        "starten Sie die Applikation erneut.",
                                exception
                        ).showAndWait();
                    }
                }
            }
        });
        // add the button Save zu the grid
        grid.add(btnSave, x+1, y);

        // return the grid with all the form
        return grid;
    }

    private Alert wrongInput(String field, String text) {
        return new Dialog().getError(
                "Eingabefehler",
                field,
                text
        );
    }
}
