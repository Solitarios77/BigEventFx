package com.solitarios.module;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    @FXML
    private VBox userCenterBox;
    @FXML
    private Region userCenterBtn;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private void onShowUserCenterBox() {
        if (userCenterBox.isVisible()) {
            userCenterBox.setVisible(false);
            userCenterBtn.setRotate(90);
        }else {
            userCenterBox.setVisible(true);
            userCenterBtn.setRotate(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategoryView();
    }

    @FXML
    private void loadCategoryView() {
        loadView("/com/solitarios/module/category/category-view.fxml");
    }

    private void loadView(String name) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomepageController.class.getResource(name));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void loadArticleView() {
        loadView("/com/solitarios/module/article/article-view.fxml");
    }

    @FXML
    private void loadUserInfoUpdate() {
        loadView("/com/solitarios/module/user/userinfo-update.fxml");
    }
    @FXML
    private void loadUserPwdUpdate() {
        loadView("/com/solitarios/module/user/user-password-update.fxml");
    }

    @FXML
    private void close() {
        ((Stage) userCenterBox.getScene().getWindow()).close();
    }
}
