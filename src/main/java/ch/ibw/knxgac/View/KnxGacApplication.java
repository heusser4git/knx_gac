package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.*;
import ch.ibw.knxgac.Repository.Database.SqlDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class KnxGacApplication extends Application {
    private Stage stage = null;
    private Configuration configuration = null;
    private Controller controller = null;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        try {
            this.controller = new Controller();
        } catch (SQLException e) {
            // TODO urs errorhandling
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO urs errorhandling
            e.printStackTrace();
        }
        try {
            // get the configuration out of the config-file
            this.configuration = controller.getConfiguration();
        } catch (ClassNotFoundException e) {
            // TODO urs: errorhandling
            e.printStackTrace();
        }

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
         * Beispiel für das holen aller Projekte
         */
//        try {
//            Project filter = new Project(29);
//            ArrayList<Project> projects = controller.selectObject(filter);
//            for(Project p : projects) {
//                System.out.println(p.getId() + " " + p.getName());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        // Check if DB-Connection ok, otherwise open config-Tab
        boolean configGui;
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
        GuiConfig guiConfig = new GuiConfig();
        // get the GridPane from outsourced Class "gridConfig"
        GridPane gridConfiguration = guiConfig.getConfigurationGrid(this.stage, this.controller, this.configuration, this.tabPane);
        Tab tabConfig = new Tab("Systemkonfiguration");
        tabConfig.setClosable(false);
        tabConfig.setContent(gridConfiguration);

        // Tab Project
        GuiProject guiProject = new GuiProject();
        GridPane gridProject = guiProject.getProjectGrid();
        Tab tabProject = new Tab("Projekt");
        tabProject.setClosable(false);
        tabProject.setContent(gridProject);

        // Tab Maingroup
        GuiMaingroup guiMaingroup = new GuiMaingroup();
        GridPane gridMaingroup = guiMaingroup.getMaingroupGrid();
        Tab tabMaingroup = new Tab("Hauptgruppe");
        tabMaingroup.setClosable(false);
        tabMaingroup.setContent(gridMaingroup);

        // Tab Middlegroup
        GuiMiddlegroup guiMiddlegroup = new GuiMiddlegroup();
        GridPane gridMiddlegroup = guiMiddlegroup.getMiddlegroupGrid();
        Tab tabMiddlegroup = new Tab("Mittelgruppe");
        tabMiddlegroup.setClosable(false);
        tabMiddlegroup.setContent(gridMiddlegroup);

        // Tab Address
        GuiAddress guiAddress = new GuiAddress();
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
        if(configGui) {
            tabProject.setDisable(true);
            tabMaingroup.setDisable(true);
            tabMiddlegroup.setDisable(true);
            tabAddress.setDisable(true);
//                tabObject.setDisable(true);
            tabPane.getSelectionModel().select(tabConfig);
        }


        Scene scene = new Scene(tabPane, 520, 440);
        stage.setTitle("KNX Group Address Creator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}


}

