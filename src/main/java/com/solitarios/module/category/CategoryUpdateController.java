package com.solitarios.module.category;

import com.solitarios.bean.Category;
import com.solitarios.utils.StringUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryUpdateController implements Initializable {
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private TextField categoryAliasTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private void close() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    private void confirm(ActionEvent actionEvent) {
        if (!StringUtil.hasLength(categoryNameTextField.getText())
                || !StringUtil.hasLength(categoryAliasTextField.getText())) {
            categoryNameTextField.setPromptText("请输入分类名！");
            categoryNameTextField.setStyle("-fx-prompt-text-fill: red");
            categoryAliasTextField.setPromptText("请输入分类别名！");
            categoryAliasTextField.setStyle("-fx-prompt-text-fill: red");
            return;
        }
        confirmActionEventHandlerProperty().get().handle(actionEvent);
        close();
    }

    private ObjectProperty<Category> category;

    public ObjectProperty<Category> categoryProperty() {
        if (category == null) {
            category = new SimpleObjectProperty<>();
        }
        return category;
    }

    public Category getCategory() {
        return categoryProperty().get();
    }

    public void setCategory(Category category) {
        categoryProperty().set(category);
    }

    public CategoryUpdateController() {
    }

    public static void show(Stage owner, Category category,
                            EventHandler<ActionEvent> confirmActionEventHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(CategoryUpdateController.class.getResource("category-update.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            CategoryUpdateController controller = fxmlLoader.getController();
            controller.setCategory(category);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryNameTextField.textProperty().bindBidirectional(categoryProperty(), new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                if (object == null) {
                    return null;
                }
                return object.getCategoryName();
            }

            @Override
            public Category fromString(String string) {
                if (categoryProperty().get() == null) {
                    categoryProperty().set(new Category());
                }
                categoryProperty().get().setCategoryName(string);
                return categoryProperty().get();
            }
        });
        categoryAliasTextField.textProperty().bindBidirectional(categoryProperty(), new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                if (object == null) {
                    return null;
                }
                return object.getCategoryAlias();
            }

            @Override
            public Category fromString(String string) {
                if (categoryProperty().get() == null) {
                    categoryProperty().set(new Category());
                }
                categoryProperty().get().setCategoryAlias(string);
                return categoryProperty().get();
            }
        });
        categoryNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.booleanValue()) {
                categoryNameTextField.setPromptText("");
                categoryNameTextField.setStyle("");
            }
        });
        categoryAliasTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.booleanValue()) {
                categoryAliasTextField.setPromptText("");
                categoryAliasTextField.setStyle("");
            }
        });
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
}
