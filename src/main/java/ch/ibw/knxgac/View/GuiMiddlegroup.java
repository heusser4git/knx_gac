package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Control.Controller;
import ch.ibw.knxgac.Model.MainGroup;
import ch.ibw.knxgac.Model.MiddleGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuiMiddlegroup {

    private Controller controller;
    ArrayList<MainGroup> mainGroups = new ArrayList<>();
    ArrayList<MiddleGroup> middleGroups = new ArrayList<>();
    int idMaingroup;

    private void update(){
        try {
            this.mainGroups = this.controller.selectObject(new MainGroup());
            this.middleGroups = this.controller.selectObject(new MiddleGroup());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GuiMiddlegroup(Controller controller){
        this.controller = controller;
    }

    private ObservableList<ChoiceBoxItem> maingroupItems(){
        ObservableList<ChoiceBoxItem> items = FXCollections.observableArrayList();
        for (MainGroup m : this.mainGroups){
            if(m.getIdProject() == KnxGacApplication.currentProjectID){
                items.add(new ChoiceBoxItem(m.getId(),m.getNumber() +" " + m.getName()));
            }
        }
        return items;
    }

    private ObservableList<ChoiceBoxItem> middelgroupItems(){
        ObservableList<ChoiceBoxItem> items = FXCollections.observableArrayList();
        for (MiddleGroup m : this.middleGroups){
            if(m.getIdMaingroup() == idMaingroup){
                items.add(new ChoiceBoxItem(m.getId(), m.getNumber() + " "+ m.getName()));
            }
        }
        return items;
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
        ChoiceBox cbmaingroup = new ChoiceBox();
        cbmaingroup.getItems().addAll(this.maingroupItems());
        grid.add(cbmaingroup,x+1,y);

        ListView<ChoiceBoxItem> middelGroupList = new ListView<>();
        middelGroupList.getItems().addAll(this.middelgroupItems());
        grid.add(middelGroupList, x+4,y,2,7);

        y++;
        grid.add(fieldHelper.getLable("Name"),x,y);
        TextField tfMiddelGroopname = fieldHelper.getTextField("");
        grid.add(tfMiddelGroopname,x+1,y);

        y++;
        grid.add(fieldHelper.getLable("Nummer"),x,y);

        ChoiceBox cbMiddelgroupNumber = new ChoiceBox(FXCollections.observableArrayList(0,1,2,3,4
                ,5,6,7));
        grid.add(cbMiddelgroupNumber,x+1,y);

        y++;
        Button btnCreate = new Button();
        btnCreate.setText("erstellen");
        grid.add(btnCreate,x+1,y);

        y++;
        Button btnUpdate = new Button();
        btnUpdate.setText("Update");
        grid.add(btnUpdate,x+1,y);

        // Eventhandler
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                update();
                cbmaingroup.getItems().clear();
                cbmaingroup.getItems().addAll(maingroupItems());
            }
        });

        cbmaingroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                idMaingroup = Integer.parseInt(cbmaingroup.getId());
                update();
                middelGroupList.getItems().clear();
                middelGroupList.getItems().addAll(middelgroupItems());

            }
        });


        return grid;
    }
}
