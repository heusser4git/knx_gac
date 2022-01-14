package ch.ibw.knxgac.View;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Dialog {
    public Alert getError(String title, String headerText, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
    public Alert getInformation(String title, String headerText, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
    public Alert getConfirmation(String title, String headerText, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
    public Alert getWarning(String title, String headerText, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(headerText);
        return alert;
    }
    public Alert getException(String title, String headerText, String text, Exception exception) {
        // TODO Quelle: https://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(text);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        return alert;
    }
}
