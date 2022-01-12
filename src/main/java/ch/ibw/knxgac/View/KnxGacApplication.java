package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    protected static boolean configGui = false;

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

// ObjectTemplate erstellen
//        ObjectTemplate ot = new ObjectTemplate();
//        ot.setName("Dimmen");
//        try {
//            controller.insertObject(ot);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        // Attribute erstellen
//        Attribute a = new Attribute();
//        a.setNumber(1);
//        a.setName("E/A");
//        a.setIdObjectTemplate(1);
//        try {
//            controller.insertObject(a);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        Address ad = new Address();
//        ad.setName("Adressename");
//        ad.setIdMiddlegroup(1);
//        ad.setStartAddress(3);
//        ad.setObjectTemplate(new ObjectTemplate(1));
//        try {
//            controller.insertObject(ad);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // auslesen einer adresse mit der id 1
//        try {
//            ArrayList<Address> addresses = controller.selectObject(new Address(1));
//            for (Address a : addresses) {
//                System.out.println(a);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }



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
            // add an eventhandler to the Project-Tab, which actualizes the data of the Projecttab
            tabProject.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if(tabProject.isSelected()) {
                        if(!configGui)
                            guiProject.updateProjectComboBox();

                        if(KnxGacApplication.currentProjectID>0)
                            // set currentProject as selected
                            guiProject.selectProjectFromComboBox();
                    }
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
            tabMaingroup.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if(tabMaingroup.isSelected()) {
                        guiMaingroup.updateMaingroupList(KnxGacApplication.currentProjectID);
                    }
                }
            });

            // Tab Middlegroup
            GuiMiddlegroup guiMiddlegroup = new GuiMiddlegroup(this.controller);
            GridPane gridMiddlegroup = guiMiddlegroup.getMiddlegroupGrid();
            Tab tabMiddlegroup = new Tab("Mittelgruppe");
            tabMiddlegroup.setDisable(true);
            tabMiddlegroup.setClosable(false);
            tabMiddlegroup.setContent(gridMiddlegroup);

            tabMiddlegroup.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (tabMiddlegroup.isSelected()){
                        guiMiddlegroup.update(KnxGacApplication.currentProjectID);
                    }
                }
            });

            // Tab Address
            GuiAddress guiAddress = new GuiAddress(this.controller);
            GridPane gridAddress = guiAddress.getAddressGrid();
            Tab tabAddress = new Tab("Adressen");
            tabAddress.setDisable(true);
            tabAddress.setClosable(false);
            tabAddress.setContent(gridAddress);

            tabAddress.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if(tabAddress.isSelected()){
                        guiAddress.update(KnxGacApplication.currentProjectID);
                    }
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

                    // set the actual project onto guiMaingroup
                   // guiMaingroup.laChosenProject.setText(KnxGacApplication.currentProjectName);
                    // actual Project in stage.title
                    String title = stage.getTitle();
                    int pos = title.indexOf("  /  ");
                    if(pos>0)
                        title = title.substring(0, pos);
                    stage.setTitle(title + "  /  " + KnxGacApplication.currentProjectName);
                }
            });

            Scene scene = new Scene(tabPane, 560, 440);
            stage.setScene(scene);
        }
        stage.setTitle("KNX Group Address Creator  /  Kein Projekt gewählt");
        stage.getIcons().add(new Image("knxgac.png"));

        stage.show();
        // if a alert exists, show the alert
        if(alert!=null)
            alert.showAndWait();

    }

    public static void main(String[] args) {launch();}


}

