package com.solitarios.module.article;

import com.solitarios.bean.Article;
import com.solitarios.bean.Category;
import com.solitarios.utils.StringUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArticleUpdateController implements Initializable {
    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;

    @FXML
    private StackPane imagePane;

    private Label label = new Label("请上传图片！");

    @FXML
    private void close() {
        ((Stage) titleTextField.getScene().getWindow()).close();
    }
    @FXML
    private void confirm(ActionEvent actionEvent) {
        if (!StringUtil.hasLength(titleTextField.getText())
                || !StringUtil.hasLength(textArea.getText())) {
            titleTextField.setPromptText("请输入分类名！");
            titleTextField.setStyle("-fx-prompt-text-fill: red");
            textArea.setPromptText("请输入分类别名！");
            textArea.setStyle("-fx-prompt-text-fill: red");
            return;
        }
//        if (imageView.getImage() == null) {
//            imagePane.getChildren().add(label);
//            return;
//        }
        if (categoryComboBox.getSelectionModel().getSelectedIndex() == -1) {
            categoryComboBox.setStyle("-fx-prompt-text-fill: red");
        }
        article.get().setCoverImg("http://localhost:8080/1.png");
        confirmActionEventHandlerProperty().get().handle(actionEvent);
        close();
    }

    public static void show(Stage owner, Article article, ObservableList<Category> categories,
                            EventHandler<ActionEvent> confirmActionEventHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(ArticleUpdateController.class.getResource("article-update.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            ArticleUpdateController controller = fxmlLoader.getController();
            controller.setArticle(article);
            controller.setItems(categories);
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

    private ObjectProperty<Article> article;

    public ObjectProperty<Article> articleProperty() {
        if (article == null) {
            article = new SimpleObjectProperty<>();
        }
        return article;
    }

    public Article getArticle() {
        return articleProperty().get();
    }

    public void setArticle(Article article) {
        articleProperty().set(article);
    }

    private ObjectProperty<ObservableList<Category>> items;
    public ObjectProperty<ObservableList<Category>> itemsProperty() {
        if (items == null) {
            items = new SimpleObjectProperty<>();
        }
        return items;
    }

    public ObservableList<Category> getItems() {
        return itemsProperty().get();
    }

    public void setItems(ObservableList<Category> items) {
        itemsProperty().set(items);
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
        titleTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.booleanValue()) {
                titleTextField.setPromptText("");
                titleTextField.setStyle("");
            }
        });
        textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.booleanValue()) {
                textArea.setPromptText("");
                textArea.setStyle("");
            }
        });
        imagePane.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.booleanValue()) {
                imagePane.getChildren().remove(label);
            }
        });
        titleTextField.textProperty().bindBidirectional(articleProperty(), new StringConverter<Article>() {
            @Override
            public String toString(Article object) {
                if (object == null) {
                    return null;
                }
                return object.getTitle();
            }

            @Override
            public Article fromString(String string) {
                if (articleProperty().get() == null) {
                    articleProperty().set(new Article());
                }
                articleProperty().get().setTitle(string);
                return articleProperty().get();
            }
        });
        textArea.textProperty().bindBidirectional(articleProperty(), new StringConverter<Article>() {
            @Override
            public String toString(Article object) {
                if (object == null) {
                    return null;
                }
                return object.getContent();
            }

            @Override
            public Article fromString(String string) {
                if (articleProperty().get() == null) {
                    articleProperty().set(new Article());
                }
                articleProperty().get().setContent(string);
                return articleProperty().get();
            }
        });
        categoryComboBox.itemsProperty().bindBidirectional(itemsProperty());
        categoryComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("newValue = " + newValue);
            if (newValue != null) {
                for (Category category : newValue) {
                    System.out.println("category = " + category);
                    if (category.getId() == articleProperty().get().getCategoryId()) {
                        categoryComboBox.getSelectionModel().select(category);
                    }
                }
            }
        });
        categoryComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                if (object != null) {
                    return object.getCategoryName();
                }
                return null;
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                articleProperty().get().setCategoryId(newValue.getId());
            }
        });
    }
}
