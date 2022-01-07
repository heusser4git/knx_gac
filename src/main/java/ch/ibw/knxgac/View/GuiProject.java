package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuiProject {
    private Controller controller;
    ArrayList<Project> projects = new ArrayList<>();
    ChoiceBox<ChoiceBoxItem> selectProject = new ChoiceBox<>();
    Label laChosenProject = new Label();
    Button btnDelete = new Button();
    Button btnExport = new Button();

    public GuiProject(Controller controller) {
        this.controller = controller;
//        updateProjects();
    }
    public void updateProjectChoiceBox() {
        updateProjects();
        selectProject.getItems().clear();
        selectProject.getItems().addAll(this.projectItems());
    }
    private void updateProjects() {
        try {
            // get all projects out of the DB
            this.projects = this.controller.selectObject(new Project());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<ChoiceBoxItem> projectItems() {
        ObservableList<ChoiceBoxItem> items = FXCollections.observableArrayList();
        for (Project p : this.projects) {
            // mache ChoiceBoxItems, jeweils mit der ID und dem Namen, welcher im GUI angezeigt werden soll
            // ChoiceBoxItem ist eine neue Hilfsklasse unter View - damit wir diese für jede ChoiceBox nutzen können
            items.add(new ChoiceBoxItem(p.getId(), p.getName() + " (" + p.getNumber() + ")"));
        }
        return items;
    }

    public void selectProjectFromChoiceBox() {
        for(ChoiceBoxItem cbi : selectProject.getItems()) {
            if(cbi.getId()==KnxGacApplication.currentProjectID) {
                selectProject.getSelectionModel().clearSelection();
                selectProject.getSelectionModel().select(cbi);
                break;
            }
        }
    }
    /**
     * Sets the Project choosen in the Project-ChoiceBox
     * onto the static Variables on KnxGacApplication
     */
    public void setChoosenProjectFromChoiceBox() {
        if(!selectProject.getSelectionModel().isEmpty() && selectProject.getSelectionModel().getSelectedItem().getId()>0) {
            KnxGacApplication.currentProjectID = selectProject.getSelectionModel().getSelectedItem().getId();
            String s = selectProject.getSelectionModel().getSelectedItem().getName();

            KnxGacApplication.currentProjectName = "Aktuelles Projekt: " + s;
            laChosenProject.setText(KnxGacApplication.currentProjectName);
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
        // übergebe der ChoiceBox alle ChoiceBoxItems
        selectProject.getItems().addAll(this.projectItems());
        grid.add(selectProject, x+6,y);

        y++;
        grid.add(fieldHelper.getLable("Projektnummer"), x,y);
        TextField tfProjektnummer = fieldHelper.getTextField("");
        grid.add(tfProjektnummer,x+1,y); // Todo adjust size

//        Button btnUse = new Button();
//        btnUse.setId("btnUse");
//        btnUse.setText("auswählen");
//        grid.add(btnUse,x+4,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        btnDelete.setText("Projekt löschen");
        btnDelete.setPrefWidth(80);
        btnDelete.setDisable(true);
        grid.add(btnDelete,x+4,y,2,1);

        y++;
        btnExport.setText("CSV Export");
        btnExport.setPrefWidth(80);
        btnExport.setDisable(true);
        grid.add(btnExport,x+4,y);

        //laChosenProject = fieldHelper.getLable("Kein Projekt ausgewählt","Tahoma",10,FontWeight.BOLD);
        //grid.add(laChosenProject,x+4,16,4,1);

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
                    e.printStackTrace();
                }
                updateProjects();
                // empty the choiceBox
                selectProject.getItems().clear();
                // add the project-items new to the choiceBox
                selectProject.getItems().addAll(projectItems());
            }
        });

        // choose Project
//        btnUse.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                KnxGacApplication.currentProjectID = selectProject.getSelectionModel().getSelectedItem().getId();
//                String s = selectProject.getSelectionModel().getSelectedItem().getName();
//
//                KnxGacApplication.currentProjectName = "Aktuelles Projekt: "+s;
//                laChosenProject.setText(KnxGacApplication.currentProjectName);
//
//                System.out.println(KnxGacApplication.currentProjectName);
//                System.out.println(KnxGacApplication.currentProjectID);
//
//            }
//        });

        // delete Project
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Project project = new Project(KnxGacApplication.currentProjectID);
                try {
                    // delete the actual project
                    controller.deleteObject(project);
                    // update the projects from db
                    updateProjects();
                    // empty the choiceBox
                    selectProject.getItems().clear();
                    // add the project-items new to the choiceBox
                    selectProject.getItems().addAll(projectItems());
                    KnxGacApplication.currentProjectName = "";
                    KnxGacApplication.currentProjectID = 0;
                    laChosenProject.setText("Kein Projekt gewählt.");
                    // disable buttons
                    btnDelete.setDisable(true);
                    btnExport.setDisable(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // CSV Export Project
        btnExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO implements export code
            }
        });

        return grid;
    }
}
