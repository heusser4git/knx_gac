package ch.ibw.knxgac.view;

import ch.ibw.knxgac.control.Controller;
import ch.ibw.knxgac.control.StringChecker;
import ch.ibw.knxgac.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class GuiAddress {

    private Controller controller;
    ArrayList<MainGroup> mainGroups = new ArrayList<>();
    ArrayList<MiddleGroup> middleGroups = new ArrayList<>();
    ArrayList<ObjectTemplate> objectTemplates = new ArrayList<>();
    ArrayList<Address> addresses = new ArrayList<>();
    ArrayList<Attribute> attributes = new ArrayList<>();
    ArrayList<Integer> addressstartnumber = new ArrayList<>();
    ArrayList<Integer> usedaddresses = new ArrayList<>();
    private ComboBox<ComboBoxItem> cbMaingroup = null;
    private ComboBox<ComboBoxItem> cbMiddelgroup = null;
    private ComboBox<ComboBoxItem> cbObjectTemplates = null;
    private ListView<ComboBoxItem> adressesGroupList = null;
    private ComboBox cbAdressStartNumber= null;
    int idMaingroup;
    int objectsize;

    public GuiAddress(Controller controller){
        this.controller = controller;
    }

    public void update(int idProject){
        this.upateMaingroupList(idProject);
        this.cbMaingroup.getItems().clear();
        this.cbMaingroup.getItems().addAll(this.maingroupItems());
        this.updateObjectTemplates();
        this.cbObjectTemplates.getItems().clear();
        this.cbObjectTemplates.getItems().addAll(objectTemplateItems());
    }

    private void upateMaingroupList(int idProject){
        try {
            MainGroup filterMaingroup = new MainGroup();
            filterMaingroup.setIdProject(idProject);
            this.mainGroups = this.controller.selectObject(filterMaingroup);
        }catch (SQLException e){
            new Dialog().getException("Datenbankfehler", "Hauptgruppen abrufen fehlgeschlagen", "Die Hauptgruppen können nicht abgerufen werden.", e).showAndWait();
        }
    }

    private ObservableList<ComboBoxItem> maingroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MainGroup m: mainGroups) {
            items.add(new ComboBoxItem(m.getId(), m.getNumber() + " " + m.getName(),m.getNumber()));
        }
        return items;
    }

    private void updateMiddelgroupList(int idMaingroup){
        try {
            MiddleGroup filterMiddelgroup = new MiddleGroup();
            filterMiddelgroup.setIdMaingroup(idMaingroup);
            middleGroups = controller.selectObject(filterMiddelgroup);
        }catch (SQLException e){
            new Dialog().getException("Datenbankfehler",
                    "Mittelgruppen laden fehlgeschlagen",
                    "Die gewünschten Mittelgruppen konnten nicht geladen werden.",e).showAndWait();
        }
    }

    private ObservableList<ComboBoxItem> middelgroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MiddleGroup ml: middleGroups) {
            items.add(new ComboBoxItem(ml.getId(), ml.getNumber() + " " + ml.getName(), ml.getNumber()));
        }
        return items;
    }

    private void updateObjectTemplates(){
        try {
            objectTemplates = controller.selectObject(new ObjectTemplate());
        }catch (SQLException e){
            new Dialog().getException("Datenbankfehler",
                    "Objekte laden fehlgeschlagen",
                    "Die gewünschten Objekte konnten nicht geladen werden.",e).showAndWait();
        }
    }
    
    private ObservableList<ComboBoxItem> objectTemplateItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (ObjectTemplate ot : objectTemplates) {
            items.add(new ComboBoxItem(ot.getId(), ot.getName()));
        }
        return items;
    }

    private void updateAddressList(int idMiddelgroup){
        try{
            Address filterAddress = new Address();
            filterAddress.setIdMiddlegroup(idMiddelgroup);
            addresses = controller.selectObject(filterAddress);
            Collections.sort(addresses);
            Attribute filterAttribute = new Attribute();
            attributes = controller.selectObject(filterAttribute);
            Collections.sort(attributes);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private ObservableList<ComboBoxItem> adressesItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (Address ad: addresses) {
            for (Attribute at : attributes){
                if(at.getIdObjectTemplate() == ad.getObjectTemplate().getId()){
                    items.add(new ComboBoxItem(ad.getId(),(ad.getStartAddress() + at.getNumber())+" " +
                            ad.getName()+" " + at.getName()));
                }
            }
        }
        return items;
    }

    private void updateAddressstartnumber(){
        ObjectTemplate objectTemplate = new ObjectTemplate(cbObjectTemplates.getSelectionModel().getSelectedItem().getId());
        objectsize = objectTemplate.getAttributes().size();
        addressstartnumber.clear();
        usedaddresses.clear();
        for (int i = 0; i < 255; i++) {
            addressstartnumber.add(i);

        }
        for (Address ad : addresses) {
            for (Attribute at : attributes) {
                if(at.getIdObjectTemplate() == ad.getObjectTemplate().getId()){
                    usedaddresses.add(ad.getStartAddress() + at.getNumber());
                }
            }
        }
        // the you can't use the Address 0/0/0 because it's a KNX System Address
        if (cbMaingroup.getSelectionModel().getSelectedItem().getNumber() == 0 &&
                cbMiddelgroup.getSelectionModel().getSelectedItem().getNumber() == 0){
         addressstartnumber.remove(0);
        }
        for (Integer in: usedaddresses) {
            addressstartnumber.remove(in);
        }
        for (int i = 0; i < objectsize; i++) {
            for(Integer in: addressstartnumber){
                if(in == i){

                }
            }
        }
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
        cbMaingroup = new ComboBox<>();
        cbMaingroup.getItems().addAll(this.maingroupItems());
        grid.add(cbMaingroup,x+1,y);

        adressesGroupList = new ListView<>();
        adressesGroupList.getItems().addAll(this.adressesItems());
        grid.add(adressesGroupList, x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Mittelgruppe"),x,y);
        cbMiddelgroup = new ComboBox<>();
        cbMiddelgroup.getItems().addAll(this.middelgroupItems());
        grid.add(cbMiddelgroup,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Objekt"),x,y);
        cbObjectTemplates = new ComboBox<>();
        cbObjectTemplates.getItems().addAll(this.objectTemplateItems());
        grid.add(cbObjectTemplates,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Name"),x,y);
        TextField tfAdressName = fieldHelper.getTextField("");
        grid.add(tfAdressName,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Startadresse"),x,y);
        cbAdressStartNumber =new ComboBox<>();
        cbAdressStartNumber.getItems().addAll(addressstartnumber);
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
                adressesGroupList.getItems().clear();
            }
        });

        btnCreate.setOnAction(actionEvent -> {
            if(tfAdressName.getText().isEmpty() == false &&
                cbMaingroup.getSelectionModel().isEmpty() == false &&
                cbMiddelgroup.getSelectionModel().isEmpty() == false &&
                cbObjectTemplates.getSelectionModel().isEmpty() == false &&
                cbAdressStartNumber.getSelectionModel().isEmpty() == false){
                if(StringChecker.checkStringLettersSpacesNumbersUmlaute(tfAdressName.getText())){
                    Address address = new Address();
                    address.setStartAddress((Integer) cbAdressStartNumber.getSelectionModel().getSelectedItem());
                    address.setName(tfAdressName.getText());
                    address.setIdMiddlegroup(cbMiddelgroup.getSelectionModel().getSelectedItem().getId());
                    address.setObjectTemplate(new ObjectTemplate(cbObjectTemplates.getSelectionModel().getSelectedItem().getId()));
                    try {
                        controller.insertObject(address);
                    }catch (SQLException e){
                        new Dialog().getException("Datenbankfehler",
                                "Addresse erstellen fehlgeschlagen",
                                "Die gewünschten Addressen konnten nicht erstellt werden.",e).showAndWait();
                    }
                    updateAddressList(cbMiddelgroup.getSelectionModel().getSelectedItem().getId());
                    adressesGroupList.getItems().clear();
                    adressesGroupList.getItems().addAll(adressesItems());
                    updateAddressstartnumber();
                    cbAdressStartNumber.getItems().clear();
                    cbAdressStartNumber.getItems().addAll(addressstartnumber);
                }else {
                    new Dialog().getInformation(
                            "Nicht erlaubte Eingabe",
                            "Adresse wurde nicht angelegt",
                            "Es sind keine Sonderzeichen wie z.B. $/@ erlaubt").showAndWait();
                }
            }else{
                new Dialog().getInformation(
                        "Leere Eingabefelder",
                        "Adresse wurde nicht angelegt",
                        "Das Eingabefeld und die Comboboxen dürfen nicht leer sein.").showAndWait();
            }

        });

        cbMiddelgroup.setOnAction(actionEvent -> {
            if(!cbMiddelgroup.getSelectionModel().isEmpty()){
                updateAddressList(cbMiddelgroup.getSelectionModel().getSelectedItem().getId());
                adressesGroupList.getItems().clear();
                adressesGroupList.getItems().addAll(adressesItems());
                updateAddressstartnumber();
                cbAdressStartNumber.getItems().clear();
                cbAdressStartNumber.getItems().addAll(addressstartnumber);
            }
        });
        return grid;
    }
}
