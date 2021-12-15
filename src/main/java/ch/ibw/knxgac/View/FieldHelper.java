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
    public Label getLable(String lable, String schriftart, int fontSize, FontWeight fontStyle) {
        Font font = Font.font(schriftart, fontStyle, fontSize);
        Label l = new Label(lable);
        l.setFont(font);
        return l;
    }
    public TextField getTextField(String defaulvalue) {
        return new TextField(defaulvalue);
    }
    public TextField getTextField(String defaulvalue, String id) {
        TextField tf = new TextField(defaulvalue);
        tf.setId(id);
        return tf;
    }
    public PasswordField getPasswordField(String defaultValue) {
        PasswordField pwd = new PasswordField();
        pwd.setText(defaultValue);
        return pwd;
    }

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

//    public <T extends DataInterface> ChoiceBox<T> getChoiceBox(ArrayList<T> arrayList, String defaultValue, T gewahlterWert, TextField hiddenField) {
//        // Choice Class fuer die ChoiceBox-Items
//        class Choice {
//            Integer id; String displayString;
//            Choice(Integer id, String displayString) { this.id = id; this.displayString = displayString; }
//            @Override public String toString() { return displayString; }
//        }
//
//        ObservableList<Choice> choices = FXCollections.observableArrayList();
//        if(defaultValue!=null && defaultValue.length()>0) {
//            choices.add(new Choice(null, defaultValue));
//        }
//        // fuelle die daten aus der DB in die List für die ChoiceBox
//        for (T object : arrayList) {
//            choices.add(new Choice(object.getId(), object.getName()));
//        }
//        final ChoiceBox choiceBox = new ChoiceBox(choices);
//        choiceBox.getSelectionModel().select(0);
//        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observableValue, Object oldChoice, Object newChoice) {
//                // schreibe den gewaehlten User (id) in ein hidden field
//                hiddenField.setText(String.valueOf(((Choice) newChoice).id));
//                return;
//            }
//        });
//        return choiceBox;
//    }
}
