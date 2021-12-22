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
    public GridPane getProjectGrid() {
        // TODO create the Project Grid with all Form
        // Todo create middelline
        //Project project = new Project();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15,15,15,15));

        int y = 0;
        int x = 1;
        FieldHelper fieldHelper = new FieldHelper();

        y++;
        grid.add(fieldHelper.getLable("Projekt erstellen", "Tahoma", 14, FontWeight.BOLD), x,y,2,1);
        grid.add(fieldHelper.getLable("Projekt wählen", "Tahoma", 14, FontWeight.BOLD), x+2,y,2,1);

        y++;
        grid.add(fieldHelper.getLable("Projektname"), x,y);
        TextField tfProjektname = fieldHelper.getTextField("");
        grid.add(tfProjektname,x+1,y); // Todo adjust size

        grid.add(fieldHelper.getLable("Projekt wählen"), x+2,y);
        ChoiceBox selectProject = new ChoiceBox<>();
        projectChoice(selectProject);
        //String value = (String) selectProject.getValue();
        //System.out.println(value);
        grid.add(selectProject,x+3,y);

//        y++;
//        // TODO Dieser Try/Catch-Block könnte gespart werden, wenn einfach das Array projects mit dem Constructor übergeben würde...
//        ArrayList<Project> projects = new ArrayList<>();
//        try {
//            Controller controller = new Controller();
//            projects = controller.selectObject(new Project());
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        ObservableList<ChoiceBoxItem> items = FXCollections.observableArrayList();
//        for (Project p : projects) {
//            // mache ChoiceBoxItems, jeweils mit der ID und dem Namen, welcher im GUI angezeigt werden soll
//            // ChoiceBoxItem ist eine neue Hilfsklasse unter View - damit wir diese für jede ChoiceBox nutzen können
//            items.add(new ChoiceBoxItem(p.getId(), p.getName() + " " + p.getNumber()));
//        }
//        ChoiceBox<ChoiceBoxItem> selectProject2 = new ChoiceBox<>();
//        // übergebe der ChoiceBox alle ChoiceBoxItems
//        selectProject2.getItems().addAll(items);
//        grid.add(selectProject2, x+3, y);

        y++;
        grid.add(fieldHelper.getLable("Projektnummer"), x,y);
        TextField tfProjektnummer = fieldHelper.getTextField("");
        grid.add(tfProjektnummer,x+1,y); // Todo adjust size

        Button btnCreat = new Button();
        btnCreat.setText("erstellen");
        grid.add(btnCreat,x+1,y+3);

        Button btnUse = new Button();
        btnUse.setText("auswählen");
        grid.add(btnUse,x+2,y);

        Button btnDelete = new Button();
        btnDelete.setText("Projekt löschen");
        grid.add(btnDelete,x+2,y+3);

        Button btnExport = new Button();
        btnExport.setText("CSV Export");
        grid.add(btnExport,x+2,y+4);

        Label laChossenProject = fieldHelper.getLable("Kein Projekt ausgewählt","Tahoma",10,FontWeight.BOLD);
        grid.add(laChossenProject,x+2,y+20,2,1);

        //-- Eventhandling --//
        // Creat Projekt
        btnCreat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Project project = new Project();
                project.setName(tfProjektname.getText());
                project.setNumber(Integer.parseInt(tfProjektnummer.getText()));
                try {
                    Controller controller = new Controller();
                    project.setId(controller.insertObject(project));
                    projectChoice(selectProject);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // choose Project
        btnUse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = (String) selectProject.getValue();
                String[] parts = s.split("_");
                String p1 = parts[0];
                String p2 = parts[1];
                System.out.println("p1: " + p1 + " p2: " + p2);
                try {
                    Controller controller = new Controller();
                    Project project = new Project();
                    // TODO Mitja, du könntest hier direkt nach dem Projekt suchen
                    // indem du den namen und die nummer mitgibst - in etwa so:
//                    project.setName(p1);
//                    project.setNumber(Integer.parseInt(p2));
                    // Natürlich wäre es hier viel besser, wenn man die ID bekommen würde aus der ChoiceBox
                    ArrayList<Project> result = controller.selectObject(project);
                    int i = Integer.parseInt(p2);
                    for (Project p: result) {
                        if (p.getName() == p1 || p.getNumber() == Integer.parseInt(p2)){
                            KnxGacApplication.currentProjectID = p.getId();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

//                // TODO alternativ zu selectProject hier selectProject2
//                KnxGacApplication.currentProjectID = selectProject2.getSelectionModel().getSelectedItem().getId();
//                String s = selectProject2.getSelectionModel().getSelectedItem().getName();

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
                    Controller controller = new Controller();
                    controller.deleteObject(project);
                    projectChoice(selectProject);
                    //selectProject.getItems().add(project.getName()+ "_" + project.getNumber());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return grid;
    }
    public void projectChoice(ChoiceBox selectProject){
        Controller controller = null;
        try {
            controller = new Controller();
            Project project = new Project();
            ArrayList<Project> result = controller.selectObject(project);
            for (Project p: result) {
                selectProject.getItems().add(p.getName() + "_" + p.getNumber());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
