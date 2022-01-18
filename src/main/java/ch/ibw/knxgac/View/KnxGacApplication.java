package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class KnxGacApplication extends Application {
    public static String currentProjectName;
    public static int currentProjectID;
    protected static Configuration configuration = null;
    private Controller controller = null;
    protected static boolean configGui = false;

    @Override
    public void start(Stage stage) throws IOException {
        boolean noDbError = true;   // false, if a Database-Exception occurs
        Alert alert = null; // Dialog if an Exception occurs
        try {
            this.controller = new Controller();
        } catch (SQLException e) {
            // errorhandling if a DB-Exception occurs
            noDbError = false;
            alert = new Dialog().getException(
                    "Error",
                    "Datenbankfehler",
                    "Es ist ein Fehler im Zusammenhang mit der Datenbank aufgetreten.\n" +
                            "Bitte prüfen Sie ob der Datenbankserver ordnungsgerecht funktioniert.",
                    e
            );
        }
        if(noDbError) { // if a DB-Exception occurs, we cannot get the configuration
            try {
                // get the configuration out of the config-file
                this.configuration = controller.getConfiguration();
            } catch (IOException exception) {
                alert = new Dialog().getException(
                        "Error",
                        "Fehler beim Lesen der Konfiguration",
                        "Prüfen Sie die Datei configuration.txt.\n" +
                                "Stellen Sie sicher, dass die Datei vorhanden und lesbar ist.",
                        exception
                );
            }
        }

        // Check if DB-Connection ok, otherwise open config-Tab
        if(noDbError) {// if a DB-Exception occurs, we cannot check or get the configuration
            try {
                // check if the configuration-data are complete and the
                // database is reachable, also the outputfolder is available
                configGui = !this.controller.checkConfiguration(this.configuration);
            } catch (SQLException e) {
                // if database has an error show the config-gui
                configGui = true;
            }

            // create a TabPane to place the Tabs
            TabPane tabPane = new TabPane();

            // Tab for the Configuration-Information
            GuiConfig guiConfig = new GuiConfig(stage, this.controller);
            // get the GridPane from outsourced Class "gridConfig"
            GridPane gridConfiguration = guiConfig.getConfigurationGrid(this.configuration, tabPane);
            Tab tabConfig = new Tab("Systemkonfiguration");
            tabConfig.setClosable(false);
            tabConfig.setContent(gridConfiguration);

            // Tab Project
            GuiProject guiProject = new GuiProject(this.controller);
            GridPane gridProject = guiProject.getProjectGrid();
            Tab tabProject = new Tab("Projekt");
            tabProject.setClosable(false);
            tabProject.setContent(gridProject);
            // add an eventhandler to the Project-Tab, which actualizes the data of the Projecttab
            tabProject.setOnSelectionChanged(event -> {
                if(tabProject.isSelected()) {
                    if(!configGui)
                        guiProject.updateProjectComboBox();

                    if(KnxGacApplication.currentProjectID>0)
                        // set currentProject as selected
                        guiProject.selectProjectFromComboBox();
                }
            });

            // Tab Maingroup
            GuiMaingroup guiMaingroup = new GuiMaingroup(this.controller);
            GridPane gridMaingroup = guiMaingroup.getMaingroupGrid();
            Tab tabMaingroup = new Tab("Hauptgruppe");
            tabMaingroup.setDisable(true);
            tabMaingroup.setClosable(false);
            tabMaingroup.setContent(gridMaingroup);
            // add an eventhandler to the Maingroup-Tab, which actualizes the data of the Maingrouptab
            tabMaingroup.setOnSelectionChanged(event -> {
                if(tabMaingroup.isSelected()) {
                    guiMaingroup.update(KnxGacApplication.currentProjectID);
                }
            });

            // Tab Middlegroup
            GuiMiddlegroup guiMiddlegroup = new GuiMiddlegroup(this.controller);
            GridPane gridMiddlegroup = guiMiddlegroup.getMiddlegroupGrid();
            Tab tabMiddlegroup = new Tab("Mittelgruppe");
            tabMiddlegroup.setDisable(true);
            tabMiddlegroup.setClosable(false);
            tabMiddlegroup.setContent(gridMiddlegroup);

            tabMiddlegroup.setOnSelectionChanged(event -> {
                if (tabMiddlegroup.isSelected()){
                    guiMiddlegroup.update(KnxGacApplication.currentProjectID);
                }
            });

            // Tab Address
            GuiAddress guiAddress = new GuiAddress(this.controller);
            GridPane gridAddress = guiAddress.getAddressGrid();
            Tab tabAddress = new Tab("Adressen");
            tabAddress.setDisable(true);
            tabAddress.setClosable(false);
            tabAddress.setContent(gridAddress);

            tabAddress.setOnSelectionChanged(event -> {
                if(tabAddress.isSelected()){
                    guiAddress.update(KnxGacApplication.currentProjectID);
                }
            });

            tabPane.getTabs().add(tabProject);
            tabPane.getTabs().add(tabMaingroup);
            tabPane.getTabs().add(tabMiddlegroup);
            tabPane.getTabs().add(tabAddress);
            tabPane.getTabs().add(tabConfig);
            if (configGui) {
                tabProject.setDisable(true);
                tabPane.getSelectionModel().select(tabConfig);
            }
            // if project ist choosen, show all tabs
            guiProject.selectProject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ComboBoxItem>() {
                @Override
                public void changed(ObservableValue<? extends ComboBoxItem> observableValue, ComboBoxItem comboBoxItem, ComboBoxItem t1) {
                    guiProject.setChoosenProjectFromComboBox();

                    if(KnxGacApplication.currentProjectID>0) {
                        // show all tabs
                        tabMaingroup.setDisable(false);
                        tabMiddlegroup.setDisable(false);
                        tabAddress.setDisable(false);

                        // activate delete and export buttons
                        guiProject.btnDelete.setDisable(false);
                        guiProject.btnExport.setDisable(false);
                    }

                    // actual Project in stage.title
                    String title = stage.getTitle();
                    int pos = title.indexOf("  /  ");
                    if(pos>0)
                        title = title.substring(0, pos);
                    stage.setTitle(title + "  /  " + KnxGacApplication.currentProjectName);
                }
            });

            // Handler for DELETE Project
            guiProject.btnDelete.setOnAction(actionEvent -> {
                Alert d = new Dialog().getConfirmation(
                        "Löschen bestätigen",
                        "Projekt wirklich löschen?",
                        "Wollen Sie das Projekt endgültig löschen?");
                d.showAndWait();
                if(d.getResult().getText().equalsIgnoreCase("ok")) {
                    Project project = new Project(KnxGacApplication.currentProjectID);
                    try {
                        // delete the actual project
                        controller.deleteObject(project);
                        // update the projects from db
                        guiProject.getProjects();
                        // empty the ComboBox
                        guiProject.selectProject.getItems().clear();
                        // add the project-items new to the ComboBox
                        guiProject.selectProject.getItems().addAll(guiProject.projectItems());
                        KnxGacApplication.currentProjectName = "";
                        KnxGacApplication.currentProjectID = 0;
                        // Windowtitle
                        // actual Project in stage.title
                        String title = stage.getTitle();
                        int pos = title.indexOf("  /  ");
                        if (pos > 0)
                            title = title.substring(0, pos);
                        stage.setTitle(title + "  /  Kein Projekt gewählt");
                        // disable tabs
                        tabMaingroup.setDisable(true);
                        tabMiddlegroup.setDisable(true);
                        tabAddress.setDisable(true);
                        // disable buttons
                        guiProject.btnDelete.setDisable(true);
                        guiProject.btnExport.setDisable(true);
                    } catch (SQLException e) {
                        new Dialog().getException("Datenbankfehler",
                                "Löschen fehlgeschlagen",
                                "Das Projekt konnte nicht gelöscht werden.", e).showAndWait();
                    }
                }
            });

            Scene scene = new Scene(tabPane, 560, 440);
            stage.setScene(scene);
        }
        stage.setTitle("KNX Group Address Creator  /  Kein Projekt gewählt");
        stage.getIcons().add(new Image("knxgac.png"));

        stage.show();
        // if an alert exists, show the alert
        if(alert!=null)
            alert.showAndWait();

    }

    public static void main(String[] args) {launch();}


}

