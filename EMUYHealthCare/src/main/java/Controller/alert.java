package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class alert {

    private static void applyCSS(Alert alert) {
        alert.getDialogPane().getStylesheets().add(
                alert.class.getResource("/css/alert-style.css").toExternalForm()
        );
    }

    public static void AlertEror(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        applyCSS(alert);
        alert.showAndWait();
    }

    public static void AlertWarning(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        applyCSS(alert);
        alert.showAndWait();
    }

    public static void AlertInfo(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        applyCSS(alert);
        alert.show();
    }

    public static void AlertConfirm(String title, String message, Method method, Object instance) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        applyCSS(alert);

        Optional<ButtonType> result = alert.showAndWait();

        result.ifPresent(button -> {
            if (button == ButtonType.OK) {
                try {
                    method.setAccessible(true);
                    method.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void AlertConfirm(String title, String message, Stage stageToClose) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        applyCSS(alert);

        Optional<ButtonType> result = alert.showAndWait();

        result.ifPresent(button -> {
            if (button == ButtonType.OK) {
                stageToClose.close();
            }
        });
    }
}
