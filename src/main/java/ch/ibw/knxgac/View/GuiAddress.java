package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.*;
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

public class GuiAddress {

    private Controller controller;
    ArrayList<MainGroup> mainGroups = new ArrayList<>();
    ArrayList<MiddleGroup> middleGroups = new ArrayList<>();
//    ArrayList<Attribute> attributes = new ArrayList<>();
//    ArrayList<Address> addresses = new ArrayList<>();
    private ComboBox<ComboBoxItem> cbMaingroup = null;
    private ComboBox<ComboBoxItem> cbMiddelgroup = null;
    int idMaingroup;

    public GuiAddress(Controller controller){
        this.controller = controller;
    }

    public void update(int idProject){
        this.upateMaingroupList(idProject);
        this.cbMaingroup.getItems().clear();
        this.cbMaingroup.getItems().addAll(this.maingroupItems());
    }

    private void upateMaingroupList(int idProject){
        try {
            MainGroup filterMaingroup = new MainGroup();
            filterMaingroup.setIdProject(idProject);
            this.mainGroups = this.controller.selectObject(filterMaingroup);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private ObservableList<ComboBoxItem> maingroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MainGroup m: mainGroups) {
            items.add(new ComboBoxItem(m.getId(),m.getNumber() + " " + m.getName()));
        }
        return items;
    }

    private void updateMiddelgroupList(int idMaingroup){
        try {
            MiddleGroup filterMiddelgroup = new MiddleGroup();
            filterMiddelgroup.setIdMaingroup(idMaingroup);
            middleGroups = controller.selectObject(filterMiddelgroup);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private ObservableList<ComboBoxItem> middelgroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MiddleGroup ml: middleGroups) {
            items.add(new ComboBoxItem(ml.getId(), ml.getNumber() + " " + ml.getName()));
        }
        return items;
    }

    /*
    private ObservableList<ComboBoxItem> attributesItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        // Todo Impelent Code
        return items;
    }

    private ObservableList<ComboBoxItem> adressesItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        // Todo Impelent Code
        return items;
    }

     */

    public GridPane getAddressGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        FieldHelper fieldHelper = new FieldHelper();
        int y = 0;
        int x = 1;
        
        y++;
        grid.add(fieldHelper.getLable("Adressen erstellen", "Tahoma", 14, FontWeight.BOLD), x,y,2,1);
        grid.add(fieldHelper.getLable("Adressen", "Tahoma", 14, FontWeight.BOLD), x+4,y);
        
        y++;
        grid.add(fieldHelper.getLable("Hauptgruppe"),x,y);
        cbMaingroup = new ComboBox<>();
        cbMaingroup.getItems().addAll(this.maingroupItems());
        grid.add(cbMaingroup,x+1,y);

        ListView<ComboBoxItem> adressesGroupList = new ListView<>();
//        adressesGroupList.getItems().addAll(this.adressesItems());
        grid.add(adressesGroupList, x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Mittelgruppe"),x,y);
        cbMiddelgroup = new ComboBox<>();
        cbMiddelgroup.getItems().addAll(this.middelgroupItems());
        grid.add(cbMiddelgroup,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Objekt"),x,y);
        ComboBox<ComboBoxItem> cbattributes = new ComboBox<>();
//        cbattributes.getItems().addAll(this.attributesItems());
        grid.add(cbattributes,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Name"),x,y);
        TextField tfAdressName = fieldHelper.getTextField("");
        grid.add(tfAdressName,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Startadresse"),x,y);
        ComboBox cbAdressStartNumber =new ComboBox<>();
        for (int i = 0; i < 255; i++) {
            cbAdressStartNumber.getItems().add(i);
        }

        grid.add(cbAdressStartNumber,x+1,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        cbMaingroup.setOnAction(actionEvent -> {
            if(!cbMaingroup.getSelectionModel().isEmpty()){
                idMaingroup = cbMaingroup.getSelectionModel().getSelectedItem().getId();
                updateMiddelgroupList(idMaingroup);
                cbMiddelgroup.getItems().clear();
                cbMiddelgroup.getItems().addAll(middelgroupItems());
            }
        });
        return grid;
    }
}
