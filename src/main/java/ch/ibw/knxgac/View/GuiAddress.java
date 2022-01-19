package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Control.StringChecker;
import ch.ibw.knxgac.Model.*;
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

    private final Controller controller;
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
    private ComboBox<Integer> cbAdressStartNumber= null;
    int idMaingroup;
    int objectsize;

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
            items.add(new ComboBoxItem(ot.getId(), ot.getName() + " (" + ot.getAttributes().size() + ")"));
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

    /**
     * generates the available startaddresses
     * considering the chosen template and
     * the allready used addresses on the chosen middlegroup
     *
     * => updates the addressstartnumber-combobox
     */
    private void setAdressstartnumber() {
        // generate all adresses
        ArrayList<Integer> maxStartadresses = new ArrayList<>();
        for (int i = 0; i <255; i++) {
            maxStartadresses.add(i);
        }
        int idTemplate = cbObjectTemplates.getSelectionModel().getSelectedItem().getId();
        int idMiddlegroup = cbMiddelgroup.getSelectionModel().getSelectedItem().getId();
        ArrayList<Integer> startadresses = new ArrayList<>();
        try {
            // get the used adresses in dies middlegroup
            usedaddresses = controller.selectObject(new MiddleGroup(idMiddlegroup)).get(0).usedAddresses();
            // get the chosen template
            ObjectTemplate objectTemplate = controller.selectObject(new ObjectTemplate(idTemplate)).get(0);
            // generate the available startadresses
            startadresses = objectTemplate.availableStartadresses(maxStartadresses, usedaddresses);
        } catch (SQLException exception) {
            new Dialog().getException(
                    "Datenbankfehler",
                    "Datenabfrage fehlgeschlagen",
                    "Daten für die Startadresse konnten nicht geladen werden!",
                    exception).showAndWait();
        }
        // refresh combobox
        addressstartnumber.clear();
        addressstartnumber.addAll(startadresses);

            // you can't use the Address 0/0/0 because it's a KNX System Address
            if (cbMaingroup.getSelectionModel().getSelectedItem().getNumber() == 0 &&
                    cbMiddelgroup.getSelectionModel().getSelectedItem().getNumber() == 0){
                addressstartnumber.remove(0);
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
        cbMaingroup.setPrefWidth(180);
        grid.add(cbMaingroup,x+1,y);

        adressesGroupList = new ListView<>();
        adressesGroupList.getItems().addAll(this.adressesItems());
        grid.add(adressesGroupList, x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Mittelgruppe"),x,y);
        cbMiddelgroup = new ComboBox<>();
        cbMiddelgroup.getItems().addAll(this.middelgroupItems());
        cbMiddelgroup.setPrefWidth(180);
        grid.add(cbMiddelgroup,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Objekt"),x,y);
        cbObjectTemplates = new ComboBox<>();
        cbObjectTemplates.getItems().addAll(this.objectTemplateItems());
        cbObjectTemplates.setPrefWidth(180);
        grid.add(cbObjectTemplates,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Name"),x,y);
        TextField tfAdressName = fieldHelper.getTextField("");
        grid.add(tfAdressName,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Startadresse"),x,y);
        cbAdressStartNumber = new ComboBox<>();
        cbAdressStartNumber.getItems().addAll(addressstartnumber);
        cbAdressStartNumber.setPrefWidth(180);
        grid.add(cbAdressStartNumber,x+1,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        btnCreate.setPrefWidth(100);
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
            if(!tfAdressName.getText().isEmpty() &&
                !cbMaingroup.getSelectionModel().isEmpty() &&
                !cbMiddelgroup.getSelectionModel().isEmpty() &&
                !cbObjectTemplates.getSelectionModel().isEmpty() &&
                !cbAdressStartNumber.getSelectionModel().isEmpty()){
                if(StringChecker.checkStringLettersSpacesNumbersUmlaute(tfAdressName.getText())){
                    if(StringChecker.checkStringFirstDigitIsLetter(tfAdressName.getText())){
                        Address address = new Address();
                        address.setStartAddress(cbAdressStartNumber.getSelectionModel().getSelectedItem());
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
                        setAdressstartnumber();
                        cbAdressStartNumber.getItems().clear();
                        cbAdressStartNumber.getItems().addAll(addressstartnumber);
                    }else {
                        new Dialog().getInformation(
                                "Nicht erlaubte Eingabe",
                                "Adresse wurde nicht angelegt",
                                "Es sind keine Zahlen an erster Stelle erlaubt").showAndWait();
                    }
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
                updateObjectTemplates();
                cbObjectTemplates.getItems().clear();
                cbObjectTemplates.getItems().addAll(objectTemplateItems());
            }
        });

        cbObjectTemplates.setOnAction(actionEvent -> {
            if(!cbObjectTemplates.getSelectionModel().isEmpty() && !cbMiddelgroup.getSelectionModel().isEmpty()){
                setAdressstartnumber();
                cbAdressStartNumber.getItems().clear();
                cbAdressStartNumber.getItems().addAll(addressstartnumber);
            }
        });
        return grid;
    }
}
