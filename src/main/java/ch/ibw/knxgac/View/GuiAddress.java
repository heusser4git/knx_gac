package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.Address;
import ch.ibw.knxgac.Model.Attribute;
import ch.ibw.knxgac.Model.MainGroup;
import ch.ibw.knxgac.Model.MiddleGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class GuiAddress {

    private Controller controller;
    ArrayList<MainGroup> mainGroups = new ArrayList<>();
    ArrayList<MiddleGroup> middleGroups = new ArrayList<>();
    ArrayList<Attribute> attributes = new ArrayList<>();
    ArrayList<Address> addresses = new ArrayList<>();
    
    private void update(){
        
    }

    public GuiAddress(Controller controller){
        this.controller = controller;
    }
    
    private ObservableList<ComboBoxItem> maingroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        // Todo Impelent Code
        return items;
    }

    private ObservableList<ComboBoxItem> middelgroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        // Todo Impelent Code
        return items;
    }

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
        ChoiceBox<ComboBoxItem> cbmaingroup = new ChoiceBox();
        cbmaingroup.getItems().addAll(this.maingroupItems());
        grid.add(cbmaingroup,x+1,y);

        ListView<ComboBoxItem> adressesGroupList = new ListView<>();
        adressesGroupList.getItems().addAll(this.adressesItems());
        grid.add(adressesGroupList, x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Mittelgruppe"),x,y);
        ChoiceBox<ComboBoxItem> cbmiddelgroup = new ChoiceBox();
        cbmiddelgroup.getItems().addAll(this.middelgroupItems());
        grid.add(cbmiddelgroup,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Objekt"),x,y);
        ChoiceBox<ComboBoxItem> cbattributes = new ChoiceBox();
        cbattributes.getItems().addAll(this.attributesItems());
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

        y++;
        Button btnUpdate = new Button();
        btnUpdate.setText("Update");
        grid.add(btnUpdate,x+1,y);
        
        return grid;
    }
}
