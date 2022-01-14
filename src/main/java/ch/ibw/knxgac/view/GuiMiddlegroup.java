package ch.ibw.knxgac.view;

import ch.ibw.knxgac.control.Controller;
import ch.ibw.knxgac.control.StringChecker;
import ch.ibw.knxgac.model.MainGroup;
import ch.ibw.knxgac.model.MiddleGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuiMiddlegroup {

    private Controller controller;
    ArrayList<MainGroup> mainGroups = new ArrayList<>();
    ArrayList<MiddleGroup> middleGroups = new ArrayList<>();
    ArrayList<Integer> middelgroupnumber = new ArrayList<>();
    ArrayList<Integer> usednumbers = new ArrayList<>();
    private ComboBox<ComboBoxItem> cbMaingroup = null;
    private ListView<ComboBoxItem> middelGroupList = new ListView<>();
    private ComboBox cbMiddlegroupNumber = null;
    int idMaingroup;

    public GuiMiddlegroup(Controller controller){
        this.controller = controller;
    }

    private void updateMaingroupList(int idProject){
        try {
            MainGroup filterMaingroup = new MainGroup();
            filterMaingroup.setIdProject(idProject);
            this.mainGroups = this.controller.selectObject(filterMaingroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMiddelgroupList(int idMaingroup){
        try{
            MiddleGroup filterMiddelgroup = new MiddleGroup();
            filterMiddelgroup.setIdMaingroup(idMaingroup);
            this.middleGroups = this.controller.selectObject(filterMiddelgroup);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(int idProject){
        middelGroupList.getItems().clear();
        this.updateMaingroupList(idProject);
        this.cbMaingroup.getItems().clear();
        this.cbMaingroup.getItems().addAll(this.maingroupItems());

    }

    private ObservableList<ComboBoxItem> maingroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MainGroup m : this.mainGroups){
                items.add(new ComboBoxItem(m.getId(),m.getNumber() +" " + m.getName()));
        }
        return items;
    }

    private ObservableList<ComboBoxItem> middelgroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MiddleGroup m : this.middleGroups){
            if(m.getIdMaingroup() == idMaingroup){
                items.add(new ComboBoxItem(m.getId(), m.getNumber() + " "+ m.getName()));
            }
        }
        return items;
    }
    
    private void updateMiddelgroupNumber(){
        middelgroupnumber.clear();
        usednumbers.clear();
        for (int i = 0; i < 8; i++) {
            middelgroupnumber.add(i);
        }
        for (MiddleGroup mg: middleGroups) {
            usednumbers.add(mg.getNumber());
        }
        for (Integer in :usednumbers) {
            middelgroupnumber.remove(in);
        }
    }

    public GridPane getMiddlegroupGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        FieldHelper fieldHelper = new FieldHelper();
        int y = 0;
        int x = 1;

        y++;
        grid.add(fieldHelper.getLable("Mittelgruppe erstellen", "Tahoma", 14, FontWeight.BOLD), x,y,2,1);
        grid.add(fieldHelper.getLable("Mittelgruppen", "Tahoma", 14, FontWeight.BOLD), x+4,y);

        y++;
        grid.add(fieldHelper.getLable("Hauptgruppe"),x,y);
        cbMaingroup = new ComboBox<>();
        cbMaingroup.getItems().addAll(this.maingroupItems());
        cbMaingroup.setPromptText("Auswählen");
        grid.add(cbMaingroup,x+1,y);

        middelGroupList.getItems().addAll(this.middelgroupItems());
        grid.add(middelGroupList, x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Name"),x,y);
        TextField tfMiddleGroupname = fieldHelper.getTextField("");
        grid.add(tfMiddleGroupname,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Nummer"),x,y);
        cbMiddlegroupNumber = new ComboBox();
        grid.add(cbMiddlegroupNumber,x+1,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        btnCreate.setOnAction(actionEvent -> {
            if(tfMiddleGroupname.getText().isEmpty() == false &&
                    cbMiddlegroupNumber.getSelectionModel().isEmpty() == false &&
                    cbMaingroup.getSelectionModel().isEmpty() == false) {
                if(StringChecker.checkStringLettersSpacesNumbersUmlaute(tfMiddleGroupname.getText())){
                    MiddleGroup middleGroup = new MiddleGroup();
                    middleGroup.setName(tfMiddleGroupname.getText());
                    middleGroup.setIdMaingroup(idMaingroup);
                    middleGroup.setNumber((Integer) cbMiddlegroupNumber.getSelectionModel().getSelectedItem());
                    try{
                        controller.insertObject(middleGroup);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    updateMaingroupList(KnxGacApplication.currentProjectID);
                    updateMiddelgroupList(idMaingroup);
                    middelGroupList.getItems().clear();
                    middelGroupList.getItems().addAll(middelgroupItems());
                    updateMiddelgroupNumber();
                    cbMiddlegroupNumber.getItems().clear();
                    cbMiddlegroupNumber.getItems().addAll(middelgroupnumber);
                }else{
                    new Dialog().getInformation(
                            "Nicht erlaubte Eingabe",
                            "Mittelgrppe wurde nicht angelegt",
                            "Es sind keine Sonderzeichen wie z.B. $/@ erlaubt").showAndWait();
                }
            }else {
                new Dialog().getInformation(
                        "Leere Eingabefelder",
                        "Mittelgruppe wurde nicht angelegt",
                        "Das Eingabefeld und die Comboboxen dürfen nicht leer sein.").showAndWait();
            }
        });

        cbMaingroup.setOnAction((event) -> {
            if(!this.cbMaingroup.getSelectionModel().isEmpty()) {
                idMaingroup = this.cbMaingroup.getSelectionModel().getSelectedItem().getId();
                updateMiddelgroupList(idMaingroup);
                middelGroupList.getItems().clear();
                middelGroupList.getItems().addAll(middelgroupItems());
                updateMiddelgroupNumber();
                cbMiddlegroupNumber.getItems().clear();
                cbMiddlegroupNumber.getItems().addAll(middelgroupnumber);
            }
        });
        return grid;
    }
}
