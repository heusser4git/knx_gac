package ch.ibw.knxgac.view;

import ch.ibw.knxgac.control.Controller;
import ch.ibw.knxgac.control.StringChecker;
import ch.ibw.knxgac.model.MainGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuiMaingroup {

    private Controller controller;
    ArrayList<MainGroup> mainGroups = new ArrayList<>();
    ArrayList<Integer> maingroupnumber = new ArrayList<>();
    ArrayList<Integer> usednumbers = new ArrayList<>();
    private ListView<ComboBoxItem> list = null;
    private ComboBox cbMaingroupnumber = null;

    public GuiMaingroup(Controller controller){
        this.controller = controller;
    }

    private void updateMaingroups(int idProject) {
        try{
            MainGroup filterMaingroup = new MainGroup();
            filterMaingroup.setIdProject(idProject);
            this.mainGroups = this.controller.selectObject(filterMaingroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int idProject) {
        this.updateMaingroups(idProject);
        this.list.getItems().clear();
        this.list.getItems().addAll(this.mgroupItems());
        updateMaingroupNumbers();
        cbMaingroupnumber.getItems().clear();
        cbMaingroupnumber.getItems().addAll(maingroupnumber);
    }

    private ObservableList<ComboBoxItem> mgroupItems(){
        ObservableList<ComboBoxItem> items = FXCollections.observableArrayList();
        for (MainGroup m : this.mainGroups){
            // TODO wenn du meine methode "updateMaingroupList(int idProject)" nutzt erübrigt sich die prüfung mit der currendProjectID, da nur diese aus der DB geholt werden
            if (m.getIdProject() == KnxGacApplication.currentProjectID){
                items.add(new ComboBoxItem(m.getId(), m.getNumber() + " " + m.getName()));
            }
        }
        return items;
    }

    private void updateMaingroupNumbers(){
        maingroupnumber.clear();
        usednumbers.clear();
        for (int i = 0; i < 32; i++) {
            maingroupnumber.add(i);
        }
        for (MainGroup mg: mainGroups) {
            usednumbers.add(mg.getNumber());
        }
        for (Integer in:usednumbers) {
            maingroupnumber.remove(in);
        }
    }
    
    public GridPane getMaingroupGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        FieldHelper fieldHelper = new FieldHelper();
        int y = 0;
        int x = 1;

        y++;
        grid.add(fieldHelper.getLable("Hauptgruppe erstellen", "Tahoma", 14, FontWeight.BOLD), x,y,2,1);
        grid.add(fieldHelper.getLable("Hauptgruppen", "Tahoma", 14, FontWeight.BOLD), x+4,y);

        y++;
        grid.add(fieldHelper.getLable("Name"),x,y);
        TextField tfMainGroopname = fieldHelper.getTextField("");
        grid.add(tfMainGroopname,x+1,y); // Todo adjust size

        list = new ListView<>();
        list.getItems().addAll(this.mgroupItems());

        grid.add(list,x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Nummer"),x,y);
        cbMaingroupnumber = new ComboBox();
        cbMaingroupnumber.getItems().addAll(maingroupnumber);
        grid.add(cbMaingroupnumber,x+1,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        // Eventheandler
        btnCreate.setOnAction(actionEvent -> {
            if(cbMaingroupnumber.getSelectionModel().isEmpty() == false && tfMainGroopname.getText().isEmpty() == false){
                if(StringChecker.checkStringLettersSpacesNumbersUmlaute(tfMainGroopname.getText())){
                    MainGroup mainGroup = new MainGroup();
                    mainGroup.setName(tfMainGroopname.getText());
                    mainGroup.setIdProject(KnxGacApplication.currentProjectID);
                    mainGroup.setNumber((Integer) cbMaingroupnumber.getSelectionModel().getSelectedItem());
                    try {
                        controller.insertObject(mainGroup);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    update(KnxGacApplication.currentProjectID);
                }else {
                    new Dialog().getInformation(
                            "Nicht erlaubte Eingabe",
                            "Hauptgruppe wurde nicht angelegt",
                            "Es sind keine Sonderzeichen wie z.B. $/@ erlaubt").showAndWait();
                }
            }else {
                new Dialog().getInformation(
                        "Leere Eingabefelder",
                        "Hauptgruppe wurde nicht angelegt",
                        "Das Eingabefeld und die Combobox darf nicht leer sein.").showAndWait();
            }
        });
        return grid;
    }
}
