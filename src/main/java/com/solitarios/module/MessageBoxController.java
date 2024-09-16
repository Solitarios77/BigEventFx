package com.solitarios.module;

import com.solitarios.module.category.CategoryUpdateController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageBoxController implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label titleLabel;
    @FXML
    private Label contentLabel;

    @FXML
    private void close() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
    @FXML
    private void confirm(ActionEvent actionEvent) {
        confirmActionEventHandlerProperty().get().handle(actionEvent);
        close();
    }

    public static void show(Stage owner, String title, String contentText,
                            EventHandler<ActionEvent> confirmActionEventHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(CategoryUpdateController.class.getResource("/com/solitarios/module/message-box.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            MessageBoxController controller = fxmlLoader.getController();
            controller.setTitle(title);
            controller.setContentText(contentText);
            controller.setConfirmActionEventHandler(confirmActionEventHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private StringProperty titleProperty = new SimpleStringProperty();
    private StringProperty contentTextProperty = new SimpleStringProperty();

    public String getTitle() {
        return titleProperty.get();
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public void setTitle(String titleProperty) {
        this.titleProperty.set(titleProperty);
    }

    public String getContentText() {
        return contentTextProperty.get();
    }

    public StringProperty contentTextProperty() {
        return contentTextProperty;
    }

    public void setContentText(String contentTextProperty) {
        this.contentTextProperty.set(contentTextProperty);
    }

    private ObjectProperty<EventHandler<ActionEvent>> confirmActionEventHandler = new SimpleObjectProperty<>();

    public ObjectProperty<EventHandler<ActionEvent>> confirmActionEventHandlerProperty() {
        if (confirmActionEventHandler.get() == null) {
            confirmActionEventHandler.set(event -> System.out.println("event = " + event));
        }
        return confirmActionEventHandler;
    }

    public void setConfirmActionEventHandler(EventHandler<ActionEvent> confirmActionEventHandler) {
        confirmActionEventHandlerProperty().set(confirmActionEventHandler);
    }

    public EventHandler<ActionEvent> getConfirmActionEventHandler() {
        return confirmActionEventHandlerProperty().get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleProperty.bindBidirectional(titleLabel.textProperty());
        contentTextProperty.bindBidirectional(contentLabel.textProperty());
    }
}
