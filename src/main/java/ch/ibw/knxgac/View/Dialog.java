package ch.ibw.knxgac.View;

import javafx.scene.control.Alert;

public class Dialog {
    public Alert getError(String headerText, String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
    public Alert getInformation(String headerText, String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
    public Alert getConfirmation(String headerText, String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
}
