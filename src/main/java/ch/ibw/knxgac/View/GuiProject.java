package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuiProject {
    private Controller controller;
    ArrayList<Project> projects = new ArrayList<>();
    ComboBox<ComboBoxItem> selectProject = new ComboBox<>();
    Label laChosenProject = new Label();
    Button btnDelete = new Button();
    Button btnExport = new Button();

    public GuiProject(Controller controller) {
        this.controller = controller;
//        updateProjects();
    }
    public void updateProjectComboBox() {
        updateProjects();
        selectProject.getItems().clear();
        selectProject.getItems().addAll(this.projectItems());
    }
    protected void updateProjects() {
        try {
            // get all projects out of the DB
            this.projects = this.controller.selectObject(new Project());
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void selectProjectFromComboBox() {
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
    public void setChoosenProjectFromComboBox() {
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
                    e.printStackTrace();
                }
                updateProjects();
                // empty the ComboBox
                selectProject.getItems().clear();
                // add the project-items new to the ComboBox
                selectProject.getItems().addAll(projectItems());
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
