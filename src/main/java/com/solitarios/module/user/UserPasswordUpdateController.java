package com.solitarios.module.user;

import com.solitarios.bean.Result;
import com.solitarios.service.UserService;
import com.solitarios.utils.Alerts;
import com.solitarios.utils.StringUtil;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserPasswordUpdateController implements Initializable {
    @FXML
    private PasswordField oldPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private PasswordField rePasswordTextField;
    @FXML
    private Button confirm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirm.setDisable(true);
        oldPasswordTextField.textProperty().addListener(getStringChangeListener());
        newPasswordTextField.textProperty().addListener(getStringChangeListener());
        rePasswordTextField.textProperty().addListener(getStringChangeListener());
        oldPasswordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            oldPasswordTextField.setPromptText("");
            oldPasswordTextField.setStyle("");
        });
        newPasswordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            newPasswordTextField.setPromptText("");
            newPasswordTextField.setStyle("");
        });
        rePasswordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            rePasswordTextField.setPromptText("");
            rePasswordTextField.setStyle("");
        });
    }

    private ChangeListener<String> getStringChangeListener() {
        return (observable, oldValue, newValue) -> confirm.setDisable(false);
    }

    @FXML
    private void confirm() {
        String oldPwd = oldPasswordTextField.getText();
        String newPwd = newPasswordTextField.getText();
        String rePwd = rePasswordTextField.getText();
        if (!StringUtil.hasLength(oldPwd)) {
            warring(oldPasswordTextField, "原密码不能为空");
            return;
        }
        if (!StringUtil.hasLength(newPwd)) {
            warring(newPasswordTextField, "新密码不能为空");
            return;
        }
        if (!StringUtil.hasLength(rePwd)) {
            warring(rePasswordTextField, "确认密码不能为空");
            return;
        }
        if (!newPwd.equals(oldPwd)) {
            warring(newPasswordTextField, "两次密码不一致！");
            warring(rePasswordTextField, "两次密码不一致！");
            return;
        }
        UserService userService = new UserService();
        try {
            Result result = userService.updatePwd(oldPwd, newPwd, rePwd);
            if (result.getCode() == Result.SUCCESS) {
                Alerts.showAlert(Alert.AlertType.INFORMATION, "修改成功");
            }else {
                Alerts.showAlert(Alert.AlertType.WARNING, "修改失败");
            }
        } catch (IOException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void warring(PasswordField passwordField, String promptText) {
        passwordField.setText("");
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-background-color: red");
    }
}
