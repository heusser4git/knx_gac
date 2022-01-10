package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.MainGroup;
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
    private ListView<ComboBoxItem> list = null;

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

    public void updateMaingroupList(int idProject) {
        // data update
        this.updateMaingroups(idProject);
        // delete all items from maingrouplist
        this.list.getItems().clear();
        // add refreshed items to list
        this.list.getItems().addAll(this.mgroupItems());
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
        ComboBox cb = new ComboBox(FXCollections.observableArrayList(0,1,2,3,4
        ,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31));
        grid.add(cb,x+1,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        // Eventheandler
        btnCreate.setOnAction(actionEvent -> {
            MainGroup mainGroup = new MainGroup();
            mainGroup.setName(tfMainGroopname.getText());
            mainGroup.setIdProject(KnxGacApplication.currentProjectID);
            mainGroup.setNumber((Integer) cb.getSelectionModel().getSelectedItem());
            try {
                controller.insertObject(mainGroup);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            updateMaingroupList(KnxGacApplication.currentProjectID);
        });
        return grid;
    }
}
