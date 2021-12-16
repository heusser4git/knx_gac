package ch.ibw.knxgac.View;

import ch.ibw.knxgac.Model.DataInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.DataInput;
import java.io.File;
import java.util.ArrayList;

public class FieldHelper {
    public Label getLable(String lable) {
        return new Label(lable);
    }

    /**
     * Get a label with individual Styling
     * @param lable
     * @param schriftart
     * @param fontSize
     * @param fontStyle
     * @return
     */
    public Label getLable(String lable, String schriftart, int fontSize, FontWeight fontStyle) {
        Font font = Font.font(schriftart, fontStyle, fontSize);
        Label l = new Label(lable);
        l.setFont(font);
        return l;
    }
    public TextField getTextField(String defaulvalue) {
        return new TextField(defaulvalue);
    }

    /**
     * Get a TextField with a Defaultvalue and an ID
     * @param defaulvalue
     * @param id
     * @return
     */
    public TextField getTextField(String defaulvalue, String id) {
        TextField tf = new TextField(defaulvalue);
        tf.setId(id);
        return tf;
    }

    /**
     * Get a PasswordField with a DefaultValue
     * @param defaultValue
     * @return
     */
    public PasswordField getPasswordField(String defaultValue) {
        PasswordField pwd = new PasswordField();
        pwd.setText(defaultValue);
        return pwd;
    }

    /**
     * Get a HBox with an DirectoryChooser in it
     * @param stage
     * @param buttonText
     * @param targetTextField   It is a targed Textfield (may be hidden) required to save the choosed value
     * @return
     */
    public HBox getDirectoryChooser(Stage stage, String buttonText, TextField targetTextField) {
        DirectoryChooser dc = new DirectoryChooser();
        Button btn = new Button(buttonText);
        btn.setOnAction(e -> {
            File selectedDir = dc.showDialog(stage);
            if(selectedDir!=null) {
                // write the selected directory to the targetTextField
                targetTextField.setText(selectedDir.getPath());
            }
        });
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(btn);
        return hBox;
    }

}
