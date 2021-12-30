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

    public GuiProject(Controller controller) {
        this.controller = controller;
        updateProjects();
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

    public GridPane getProjectGrid() {
        // TODO create the Project Grid with all Form
        // Todo create middelline

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15,15,15,15));



        FieldHelper fieldHelper = new FieldHelper();


        grid.add(fieldHelper.getLable("Projekt erstellen", "Tahoma", 14, FontWeight.BOLD), 2,1,3,1);
        grid.add(fieldHelper.getLable("Projekt wählen", "Tahoma", 14, FontWeight.BOLD), 6,1,3,1);


        grid.add(fieldHelper.getLable("Projektname"), 2,2,2,1);
        TextField tfProjektname = fieldHelper.getTextField("");
        grid.add(tfProjektname,4,2,2,1); // Todo adjust size

        grid.add(fieldHelper.getLable("Projekte"), 6,2,2,1);

        ChoiceBox<ChoiceBoxItem> selectProject = new ChoiceBox<>();
        // übergebe der ChoiceBox alle ChoiceBoxItems
        selectProject.getItems().addAll(this.projectItems());
        grid.add(selectProject, 8,2,2,1);


        grid.add(fieldHelper.getLable("Projektnummer"), 2,3,2,1);
        TextField tfProjektnummer = fieldHelper.getTextField("");
        grid.add(tfProjektnummer,4,3,2,1); // Todo adjust size

        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,4,4,2,1);

        Button btnUse = new Button();
        btnUse.setText("auswählen");
        grid.add(btnUse,6,3,2,1);

        Button btnDelete = new Button();
        btnDelete.setText("Projekt löschen");
        grid.add(btnDelete,6,7,2,1);

        Button btnExport = new Button();
        btnExport.setText("CSV Export");
        grid.add(btnExport,6,8,2,1);

        Label laChossenProject = fieldHelper.getLable("Kein Projekt ausgewählt","Tahoma",10,FontWeight.BOLD);
        grid.add(laChossenProject,6,16,4,1);

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
        btnUse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                KnxGacApplication.currentProjectID = selectProject.getSelectionModel().getSelectedItem().getId();
                String s = selectProject.getSelectionModel().getSelectedItem().getName();

                KnxGacApplication.currentProjectName = "Aktuelles Projekt: "+s;
                laChossenProject.setText(KnxGacApplication.currentProjectName);
                System.out.println(KnxGacApplication.currentProjectName);
                System.out.println(KnxGacApplication.currentProjectID);

            }
        });

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
                    laChossenProject.setText("Kein Projekt gewählt.");

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
