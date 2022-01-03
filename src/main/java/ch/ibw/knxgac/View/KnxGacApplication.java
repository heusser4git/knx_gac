package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class KnxGacApplication extends Application {
    public static String currentProjectName;
    public static int currentProjectID;
    private Stage stage = null;
    private Configuration configuration = null;
    private Controller controller = null;
    private TabPane tabPane;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
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
        /**
         * Beispiel für das holen aller Projekte
         */
//        try {
//            Project filter = new Project();
//            ArrayList<Project> projects = controller.selectObject(filter);
//            for(Project p : projects) {
//                System.out.println(p.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /**
         * Beispiel für das erstellen eines Projektes
         */
//        Project p = new Project();
//        p.setName("Projekt new");
//        try {
//            p.setId(controller.insertObject(p));
//            System.out.println("IdProjekt: " + p.getId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /**
         * Beispiel für das aktualisieren eines projektes
         */
//        Project p = new Project(3);
//        p.setName("aktualisiertes Projekt");
//        try {
//            controller.updateObject(p);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /**
         * Beispiel für das loeschen eines projektes
         */
//        Project p = new Project(3);
//        try {
//            controller.deleteObject(p);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /**
         * Beispiel für das Erstellen einer neuen MainGroup
         */
//        MainGroup m = new MainGroup();
//        m.setIdProject(1);
//        m.setNumber(12345);
//        m.setName("Neue Mittelgruppe 1");
//        try {
//            controller.insertObject(m);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /**
         * Beispiel für das Aendern einer  MainGroup
         */
//        MainGroup m = new MainGroup(1);
//        m.setIdProject(2);
//        m.setNumber(125345);
//        m.setName("changed: Neue Mittelgruppe 1");
//        try {
//            controller.updateObject(m);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /**
         * Beispiel für das loeschen einer  MainGroup
         */
//        MainGroup m = new MainGroup(1);
//        try {
//            controller.deleteObject(m);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }




        // Check if DB-Connection ok, otherwise open config-Tab
        boolean configGui;
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
            this.tabPane = new TabPane();

            // Tab for the Configuration-Information
            GuiConfig guiConfig = new GuiConfig(this.stage, this.controller);
            // get the GridPane from outsourced Class "gridConfig"
            GridPane gridConfiguration = guiConfig.getConfigurationGrid(this.configuration, this.tabPane);
            Tab tabConfig = new Tab("Systemkonfiguration");
            tabConfig.setClosable(false);
            tabConfig.setContent(gridConfiguration);

            // Tab Project
            GuiProject guiProject = new GuiProject(this.controller);
            GridPane gridProject = guiProject.getProjectGrid();
            Tab tabProject = new Tab("Projekt");
            tabProject.setClosable(false);
            tabProject.setContent(gridProject);

            // Tab Maingroup
            GuiMaingroup guiMaingroup = new GuiMaingroup(this.controller);
            GridPane gridMaingroup = guiMaingroup.getMaingroupGrid();
            Tab tabMaingroup = new Tab("Hauptgruppe");
            tabMaingroup.setClosable(false);
            tabMaingroup.setContent(gridMaingroup);

            // Tab Middlegroup
            GuiMiddlegroup guiMiddlegroup = new GuiMiddlegroup(this.controller);
            GridPane gridMiddlegroup = guiMiddlegroup.getMiddlegroupGrid();
            Tab tabMiddlegroup = new Tab("Mittelgruppe");
            tabMiddlegroup.setClosable(false);
            tabMiddlegroup.setContent(gridMiddlegroup);

            // Tab Address
            GuiAddress guiAddress = new GuiAddress(this.controller);
            GridPane gridAddress = guiAddress.getAddressGrid();
            Tab tabAddress = new Tab("Adressen");
            tabAddress.setClosable(false);
            tabAddress.setContent(gridAddress);

            tabPane.getTabs().add(tabProject);
            tabPane.getTabs().add(tabMaingroup);
            tabPane.getTabs().add(tabMiddlegroup);
            tabPane.getTabs().add(tabAddress);
//            tabPane.getTabs().add(tabObject);
            tabPane.getTabs().add(tabConfig);
            if (configGui) {
                tabProject.setDisable(true);
                tabMaingroup.setDisable(true);
                tabMiddlegroup.setDisable(true);
                tabAddress.setDisable(true);
//                tabObject.setDisable(true);
                tabPane.getSelectionModel().select(tabConfig);
            }

            Scene scene = new Scene(tabPane, 520, 440);
            stage.setScene(scene);
        }
        stage.setTitle("KNX Group Address Creator");
        stage.show();
        // if a alert exists, show the alert
        if(alert!=null)
            alert.showAndWait();

        //Eventhandler



    }

    public static void main(String[] args) {launch();}


}

