package com.solitarios.module.user;

import com.solitarios.bean.Result;
import com.solitarios.global.GlobalData;
import com.solitarios.service.UserService;
import com.solitarios.utils.Alerts;
import com.solitarios.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    // 登录界面
    @FXML
    private VBox loginBox;
    // 注册界面
    @FXML
    private VBox registerBox;
    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private TextField registerUsernameTextField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private PasswordField registerRePasswordField;

    private UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Image image = new Image("file:src/main/resources/image/login_bg.jpg");
//        imageView.setImage(image);
//        double oldImageWidth = image.getWidth();
//        double oldImageHeight = image.getHeight();
//        double imageRatio = oldImageWidth / oldImageHeight;
//
//        ChangeListener<Number> listener = (obs, ov, nv) -> {
//            double paneWidth = imagePane.getWidth();
//            double paneHeight = imagePane.getHeight();
//            double paneRatio = paneWidth / paneHeight;
//
//            double newImageWidth = oldImageWidth;
//            double newImageHeight = oldImageHeight;
//            if (paneRatio > imageRatio) {
//                newImageHeight = oldImageWidth / paneRatio;
//            }else if (paneRatio < imageRatio) {
//                newImageWidth = oldImageHeight * paneRatio;
//            }
//            Rectangle2D rectangle2D = new Rectangle2D((oldImageWidth - newImageWidth) / 2,
//                    (oldImageHeight - newImageHeight) / 2, newImageWidth, newImageHeight);
//            imageView.setViewport(rectangle2D);
//            imageView.setFitWidth(paneWidth);
//        };
//        imagePane.widthProperty().addListener(listener);
//        imagePane.heightProperty().addListener(listener);
    }


    @FXML
    private void changeBox() {
        if (loginBox.isVisible()) {
            loginBox.setVisible(false);
            registerBox.setVisible(true);
        } else {
            loginBox.setVisible(true);
            registerBox.setVisible(false);
        }
    }

    @FXML
    private void close() {
        ((Stage) loginBox.getScene().getWindow()).close();
    }

    @FXML
    private void login() {
        String username = loginUsernameTextField.getText();
        String password = loginPasswordField.getText();
        if (!StringUtil.hasLength(username) || !StringUtil.hasLength(password)) {
            Alerts.showAlert(Alert.AlertType.WARNING, "用户名和密码不能为空！");
            return;
        }
        Result result = null;
        try {
            result = userService.login(username, password);
        } catch (IOException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
        if (result != null) {
            if (result.getCode() == 0) {
                GlobalData.put("token", result.getData());
                FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/com/solitarios/module/homepage-view.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                    throw new RuntimeException(e);
                }
                loginBox.getScene().setRoot(root);
            } else {
                Alerts.showAlert(Alert.AlertType.ERROR, result.getMessage());
            }
        }
    }

    @FXML
    private void register() {
        String username = registerUsernameTextField.getText();
        String password = registerPasswordField.getText();
        String rePassword = registerRePasswordField.getText();
        if (!StringUtil.hasLength(username) || !StringUtil.hasLength(password) || !StringUtil.hasLength(rePassword)) {
            Alerts.showAlert(Alert.AlertType.WARNING, "用户名和密码不能为空！");
            return;
        }
        if (!password.equals(rePassword)) {
            Alerts.showAlert(Alert.AlertType.WARNING, "两次密码不同，请检查！");
            return;
        }
        Result result = null;
        try {
            result = userService.register(username, password);
        } catch (IOException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
        if (result != null) {
            if (result.getCode() == 0) {
                changeBox();
            }else {
                Alerts.showAlert(Alert.AlertType.ERROR, result.getMessage());
            }
        }
    }


}
