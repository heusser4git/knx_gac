package ch.ibw.knxgac.View;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

public class GuiMiddlegroup {
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
        ChoiceBox maingroup = new ChoiceBox();
        grid.add(maingroup,x+1,y);

        ListView<Object> list = new ListView<>();
        grid.add(list, x+4,y,2,7);

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

        return grid;
    }
}
