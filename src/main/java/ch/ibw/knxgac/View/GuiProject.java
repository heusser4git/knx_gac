package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Control.StringChecker;
import ch.ibw.knxgac.Control.StringHelper;
import ch.ibw.knxgac.Model.Project;
import ch.ibw.knxgac.Model.Repository.CsvWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final Controller controller;
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
        grid.add(fieldHelper.getLable("Projekt wählen", "Tahoma", 14, FontWeight.BOLD), x+4,y,3,1);

        y++;
        Label lblProjektname = fieldHelper.getLable("Projektname");
        grid.add(lblProjektname, x,y);
        TextField tfProjektname = fieldHelper.getTextField("");
        tfProjektname.setTooltip(new Tooltip("Bitte geben Sie als Projektname eine Zeichenfolge mit führendem Buchstaben ein. \nZum Beispiel \"A123 Hochhaus Birk\""));
        grid.add(tfProjektname,x+1,y);

        grid.add(fieldHelper.getLable("Projekte"), x+4,y);
        selectProject.setId("selectProject");
        // übergebe der ComboBox alle ComboBoxItems
        selectProject.getItems().addAll(this.projectItems());
        grid.add(selectProject, x+6,y);

        y++;
        Label lblProjektnummer = fieldHelper.getLable("Projektnummer");
        grid.add(lblProjektnummer, x,y);
        TextField tfProjektnummer = fieldHelper.getTextField("");
        tfProjektnummer.setTooltip(new Tooltip("Bitte geben Sie als Projektnummer nur Zahlen ein. \nZum Beispiel 1234"));
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
        // hide because it's not done yet
//        btnExport.setVisible(false);
        grid.add(btnExport,x+4,y);

        //-- Eventhandling --//
        // Creat Projekt
        btnCreate.setOnAction(actionEvent -> {
            Project project = new Project();
            ArrayList<String> errorsFields = new ArrayList<>();
            if(tfProjektname.getText().length()>0 && StringChecker.checkStringFirstDigitIsLetter(tfProjektname.getText())) {
                project.setName(tfProjektname.getText());
            } else {
                errorsFields.add(lblProjektname.getText());
            }
            if(tfProjektnummer.getText().length()>0 && StringChecker.checkStringOnlyNumbers(tfProjektnummer.getText())) {
                project.setNumber(Integer.parseInt(tfProjektnummer.getText()));
            } else {
                errorsFields.add(lblProjektnummer.getText());
            }
            // check if the same project allready exists
            Boolean projectAllreadyExists = false;
            try {
                ArrayList<Project> allreadyExists = controller.selectObject(project);
                if(allreadyExists.size()>0) {
                    // project allready exists
                    projectAllreadyExists = true;
                }
            } catch (SQLException e) {
                // TODO Urs
                e.printStackTrace();
            }

            if(errorsFields.size()>0) {
                new Dialog().getWarning("Falsche Eingabe",
                        "Bitte Eingabe prüfen",
                        "Folgende Felder müssen korrekt ausgefüllt werden:\n" +
                                StringHelper.implode(errorsFields, ", ")).showAndWait();
            } else if(projectAllreadyExists) {
                new Dialog().getWarning("Projekt doppelt",
                        "Projekt schon vorhanden",
                        "Das Projekt kann mit diesen Angaben nicht gespeichert werden:\n" +
                                "Ein solches exisitiert bereits.").showAndWait();
            } else {
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

        btnExport.setOnAction(actionEvent -> {
            Project filterProject = new Project(KnxGacApplication.currentProjectID);
            ArrayList<Project> actualProjects = null;
            try {
                 actualProjects = controller.selectObject(filterProject);
            } catch (SQLException e) {
                new Dialog().getException("Datenbankfehler",
                        "Projekt laden fehlgeschlagen",
                        "Das gewählte Projekt konnte nicht geladen werden.", e).showAndWait();
            }
            if(actualProjects!=null && actualProjects.size()==1 && actualProjects.get(0)!=null) {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd_HHmmss"));
                String path = KnxGacApplication.configuration.getCsvOutputpath() + "/";
                String file = date +"_KNXGAC_Projekt_" + actualProjects.get(0).getName() + "_" +actualProjects.get(0).getNumber() + ".csv";
                CsvWriter csvWriter = new CsvWriter(path+file);
                try {
                    if(csvWriter.writeProjectToCsv(actualProjects.get(0))) {
                        new Dialog().getInformation("File erstellt",
                                "Export erfolgreich",
                                "Das Projekt " + actualProjects.get(0).getName() +
                                        " wurde erfolgreich in die Datei \n" +
                                        path + "\n" + file + "\ngeschrieben.").showAndWait();
                    } else {
                        new Dialog().getWarning("File nicht erstellt",
                                "Export nicht erfolgreich",
                                "Das Projekt " + actualProjects.get(0).getName() +
                                        " konnte nicht  in die Datei \n" +
                                        path + "\n" + file + "\ngeschrieben werden.").showAndWait();
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
        });

        return grid;
    }
}
