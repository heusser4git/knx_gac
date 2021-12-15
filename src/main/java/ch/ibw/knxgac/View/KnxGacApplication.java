package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.*;
import ch.ibw.knxgac.Repository.Database.Sql;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class KnxGacApplication extends Application {
    private Stage stage = null;
    private Configuration configuration = null;
    private Controller controller = null;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.controller = new Controller();
        try {
            this.configuration = controller.getConfiguration();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        Sql sql = new Sql();
//        try {
//            sql.createConnection(configuration.getDbServertyp().name(), configuration.getDbServer(), configuration.getDbServerPort(), configuration.getDbName(), configuration.getDbUsername(), configuration.getDbPassword());
//            sql.createTable(new ObjectTemplate());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        // Check if DB-Connection ok, otherwise open config-Tab
        boolean configGui = false;
        try {
            this.controller.checkConfiguration(this.configuration);
        } catch (SQLException e) {
            configGui = true;
        }

        this.tabPane = new TabPane();

        // Tab for the Configuration-Information
        GuiConfig guiConfig = new GuiConfig();
        GridPane gridConfiguration = guiConfig.getConfigurationGrid(this.stage, this.controller, this.configuration, this.tabPane);
        Tab tabConfig = new Tab("Systemkonfiguration");
        tabConfig.setClosable(false);
        tabConfig.setContent(gridConfiguration);

        // Tab Project
        Tab tabProject = new Tab("Projekt");
        tabProject.setClosable(false);

        // Tab Maingroup
        Tab tabMaingroup = new Tab("Hauptgruppe");
        tabMaingroup.setClosable(false);

        // Tab Middlegroup
        Tab tabMiddlegroup = new Tab("Mittelgruppe");
        tabMiddlegroup.setClosable(false);

        // Tab Address
        Tab tabAddress = new Tab("Adressen");
        tabAddress.setClosable(false);

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

