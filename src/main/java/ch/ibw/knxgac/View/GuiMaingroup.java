package ch.ibw.knxgac.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;

public class GuiMaingroup {
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
        grid.add(fieldHelper.getLable("Hauptgruppe erstellen", "Tahoma", 14, FontWeight.BOLD), x,y);
        grid.add(fieldHelper.getLable("Hauptgruppen", "Tahoma", 14, FontWeight.BOLD), x+4,y);





        return grid;
    }
}
