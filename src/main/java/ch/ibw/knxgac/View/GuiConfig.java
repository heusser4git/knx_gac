package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.Configuration;
import ch.ibw.knxgac.Model.Servertyp;
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
    Stage stage = null;
    public GridPane getConfigurationGrid(Stage stage, Controller controller, Configuration configuration, TabPane tabPane) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);   // Margin
        grid.setVgap(10);   // Margin
        grid.setPadding(new Insets(15,15,15,15));   // Padding

        int y = 0;
        int x = 1;
        FieldHelper fieldHelper = new FieldHelper();
        y++;
        grid.add(fieldHelper.getLable("Datenbankkonfiguration", "Tahoma", 14, FontWeight.BOLD), x,y);

        y++;
        ChoiceBox cBservertyp = new ChoiceBox();
        for(Servertyp s : Servertyp.values()) {
            cBservertyp.getItems().add(s.name());
        }
        if(configuration.getDbServertyp() == null) {
            // no servertype choosen -> select the first
            cBservertyp.getSelectionModel().selectFirst();
        } else {
            // choose the servertyp saved in configuration
            cBservertyp.getSelectionModel().select(configuration.getDbServertyp().ordinal());
        }
        grid.add(cBservertyp, x+1, y);
        y++;
        grid.add(fieldHelper.getLable("Server"), x,y);
        TextField tfServer = fieldHelper.getTextField(configuration.getDbServer());
        grid.add(tfServer, x+1, y);
        y++;
        grid.add(fieldHelper.getLable("Server Port"), x,y);
        TextField tfServerPort = fieldHelper.getTextField(String.valueOf(configuration.getDbServerPort()));
        grid.add(tfServerPort, x+1, y);
        y++;
        grid.add(fieldHelper.getLable("Datenbank-Name"), x,y);
        TextField tfDbName = fieldHelper.getTextField(configuration.getDbName());
        grid.add(tfDbName, x+1, y);
        y++;
        grid.add(fieldHelper.getLable("User"), x,y);
        TextField tfUser = fieldHelper.getTextField(configuration.getDbUsername());
        grid.add(tfUser, x+1, y);
        y++;
        grid.add(fieldHelper.getLable("Passwort"), x,y);
        TextField tfPassword = fieldHelper.getPasswordField(configuration.getDbPassword());
        grid.add(tfPassword, x+1, y);

        y++;
        y++;
        grid.add(fieldHelper.getLable("Output", "Tahoma", 14, FontWeight.BOLD), x,y);
        y++;
        grid.add(fieldHelper.getLable("CSV Ausgabepfad"), x,y);
        TextField csvpath = fieldHelper.getTextField(configuration.getCsvOutputpath(), "csvpath");
        grid.add(csvpath, x+1, y);

        HBox hbBtnDir = new HBox(10);
        hbBtnDir.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnDir.getChildren().addAll(fieldHelper.getDirectoryChooser(this.stage, "Verzeichnis wählen", csvpath));
        grid.add(hbBtnDir, x+2, y);

        y++;
        y++;
        y++;
        Button btnSave = new Button();

        btnSave.setText("speichern");
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Configuration config = new Configuration();
                for(Servertyp s : Servertyp.values()) {
                    if(cBservertyp.getSelectionModel().getSelectedItem() == s.name()) {
                        config.setDbServertyp(s);
                        break;
                    }
                }
                config.setDbServer(tfServer.getText());
                config.setDbServerPort(Integer.parseInt(tfServerPort.getText()));
                config.setDbName(tfDbName.getText());
                config.setDbUsername(tfUser.getText());
                config.setDbPassword(tfPassword.getText());
                config.setCsvOutputpath(csvpath.getText());
                try {
                    // save the config
                    controller.saveConfiguration(config);
                    // load the config out of the file again
                    Configuration newConfiguration = controller.getConfiguration();
                    // check config against database
                    if(controller.checkConfiguration(newConfiguration)) {
                        new Dialog().getInformation(
                                "Erfolgreich gespeichert",
                                "Information",
                                "Die Konfiguration wurde erfolgreich gespeichert."
                        ).show();
                        for (Tab tab : tabPane.getTabs()) {
                            tab.setDisable(false);
                        }
                        tabPane.getSelectionModel().selectFirst();
                    }
                } catch (SQLException e) {
                    // deactivate all tabs except the selected one
                    Tab actualTab = tabPane.getSelectionModel().getSelectedItem();
                    for (Tab tab : tabPane.getTabs()) {
                        if(!tab.equals(actualTab)) {
                            tab.setDisable(true);
                        }
                    }
                    new Dialog().getError(
                            "Fehler bei der Datenbankverbindung",
                            "SQL Exception",
                            "Bitte prüfen Sie ihre Datenbankverbindungsangaben.\nEs konnte keine Verbindung zur Datenbank aufgebaut werden.\n"
                    ).show();
                } catch (ClassNotFoundException e) {
                    new Dialog().getError(
                            "Fehler beim Laden der Konfiguration",
                            "ClassNotFoundException",
                            "Die gespeicherte Konfiguration konnte nicht geladen werden."
                    ).show();
                } catch (IOException e) {
                    new Dialog().getError(
                            "Fehler beim Speichern",
                            "IO Exception",
                            "Die Konfiguration konnte nicht gespeichert werden.\n" +
                                    "Löschen Sie die Datei configuration.txt und \n" +
                                    "starten Sie die Applikation erneut."
                    ).show();
                }
            }
        });
        grid.add(btnSave, x+1, y);
        return grid;
    }
}
