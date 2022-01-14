package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.Project;
import ch.ibw.knxgac.Repository.CsvWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GuiProject {
    private Controller controller;
    protected ArrayList<Project> projects = new ArrayList<>();
    protected ComboBox<ComboBoxItem> selectProject = new ComboBox<>();
    protected Button btnDelete = new Button();
    protected Button btnExport = new Button();

    public GuiProject(Controller controller) {
        this.controller = controller;
//        updateProjects();
    }
    protected  void updateProjectComboBox() {
        getProjects();
        selectProject.getItems().clear();
        selectProject.getItems().addAll(this.projectItems());
    }
    protected void getProjects() {
        try {
            // get all projects out of the DB
            this.projects = this.controller.selectObject(new Project());
        } catch (SQLException e) {
            new Dialog().getException("Datenbankfehler", "Projekte laden", "Die vorhandenen Projekte konnten nicht geladen werden", e).showAndWait();
        }
    }

    protected ObservableList<ComboBoxItem> projectItems() {
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (Project p : this.projects) {
            // mache ComboBoxItems, jeweils mit der ID und dem Namen, welcher im GUI angezeigt werden soll
            // ComboBoxItem ist eine neue Hilfsklasse unter View - damit wir diese für jede ComboBox nutzen können
            items.add(new ComboBoxItem(p.getId(), p.getName() + " (" + p.getNumber() + ")"));
        }
        return items;
    }

    protected void selectProjectFromComboBox() {
        for(ComboBoxItem cbi : selectProject.getItems()) {
            if(cbi.getId()==KnxGacApplication.currentProjectID) {
                selectProject.getSelectionModel().clearSelection();
                selectProject.getSelectionModel().select(cbi);
                break;
            }
        }
    }
    /**
     * Sets the Project choosen in the Project-ComboBox
     * onto the static Variables on KnxGacApplication
     */
    protected void setChoosenProjectFromComboBox() {
        if(!selectProject.getSelectionModel().isEmpty() && selectProject.getSelectionModel().getSelectedItem().getId()>0) {
            KnxGacApplication.currentProjectID = selectProject.getSelectionModel().getSelectedItem().getId();
            KnxGacApplication.currentProjectName = selectProject.getSelectionModel().getSelectedItem().getName();
        }
    }

    public GridPane getProjectGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        FieldHelper fieldHelper = new FieldHelper();

        int y = 0;
        int x = 1;

        y++;
        grid.add(fieldHelper.getLable("Projekt erstellen", "Tahoma", 14, FontWeight.BOLD), x,y,2,1);
        grid.add(fieldHelper.getLable("Projekt wählen", "Tahoma", 14, FontWeight.BOLD), x+4,y,2,1);

        y++;
        grid.add(fieldHelper.getLable("Projektname"), x,y);
        TextField tfProjektname = fieldHelper.getTextField("");
        grid.add(tfProjektname,x+1,y);

        grid.add(fieldHelper.getLable("Projekte"), x+4,y);
        selectProject.setId("selectProject");
        // übergebe der ComboBox alle ComboBoxItems
        selectProject.getItems().addAll(this.projectItems());
        grid.add(selectProject, x+6,y);

        y++;
        grid.add(fieldHelper.getLable("Projektnummer"), x,y);
        TextField tfProjektnummer = fieldHelper.getTextField("");
        grid.add(tfProjektnummer,x+1,y); // Todo adjust size

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        btnDelete.setText("Projekt löschen");
        btnDelete.setId("btnDelete");
        btnDelete.setPrefWidth(80);
        btnDelete.setDisable(true);
        grid.add(btnDelete,x+4,y,2,1);

        y++;
        btnExport.setText("CSV Export");
        btnExport.setPrefWidth(80);
        btnExport.setDisable(true);
        // hide because its not done yet
//        btnExport.setVisible(false);
        grid.add(btnExport,x+4,y);

        //-- Eventhandling --//
        // Creat Projekt
        btnCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Project project = new Project();
                project.setName(tfProjektname.getText());
                project.setNumber(Integer.parseInt(tfProjektnummer.getText()));
                try {
                    project.setId(controller.insertObject(project));
                } catch (SQLException e) {
                    new Dialog().getException("Datenbankfehler",
                            "Projekt Erstellung fehlgeschlagen",
                            "Das Projekt konnte nicht erstellt werden.", e).showAndWait();
                }
                getProjects();
                // empty the ComboBox
                selectProject.getItems().clear();
                // add the project-items new to the ComboBox
                selectProject.getItems().addAll(projectItems());
            }
        });

        // delete handler is situated in KnxGacApplication.start()

        // CSV Export Project
        btnExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Project filterProject = new Project(KnxGacApplication.currentProjectID);
                ArrayList<Project> actualProjects = null;
                try {
                     actualProjects = controller.selectObject(filterProject);
                } catch (SQLException e) {
                    new Dialog().getException("Datenbankfehler",
                            "Projekt laden fehlgeschlagen",
                            "Das gewählte Projekt konnte nicht geladen werden.", e).showAndWait();
                }
                if(actualProjects!=null && actualProjects.size()==1 && actualProjects.get(0) instanceof Project) {
                    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd_HHmmss"));
                    String path = KnxGacApplication.configuration.getCsvOutputpath() + "/";
                    String file = date +"_KNXGAC_Projekt_" + actualProjects.get(0).getName() + "_" +actualProjects.get(0).getNumber() + ".csv";
                    CsvWriter csvWriter = new CsvWriter(path+file);
                    try {
                        if(csvWriter.writeProjectToCsv(actualProjects.get(0))) {
                            new Dialog().getInformation("File erstellt",
                                    "Export erfolgreich",
                                    "Das Projekt " + actualProjects.get(0).getName() +
                                            "wurde erfolgreich in die Datei \n" +
                                            path + "\n" + file + "\ngeschrieben.").showAndWait();
                        }
                    } catch (IOException e) {
                        new Dialog().getException("Ein-/Ausgabefehler",
                                "CSV-Datei nicht erstellt.",
                                "Die CSV-Datei konnte nicht erstellt werden.", e).showAndWait();
                    }
                } else {
                    new Dialog().getInformation(
                            "Projekt nicht gefunden",
                            "Projekt nicht gefunden",
                            "Projekt konnte nicht ausgegeben werden.").showAndWait();
                }
            }
        });

        return grid;
    }
}
