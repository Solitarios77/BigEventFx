package com.solitarios.utils;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showAlert(Alert.AlertType alertType, String contextText) {
        Alert alert = new Alert(alertType);
        alert.setContentText(contextText);
        alert.show();
    }
}
