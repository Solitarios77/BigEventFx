package com.solitarios.module.user;

import com.solitarios.bean.Result;
import com.solitarios.bean.User;
import com.solitarios.service.UserService;
import com.solitarios.utils.Alerts;
import com.solitarios.utils.StringUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserinfoUpdateController implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField emailTextField;

    @FXML
    private Button confirm;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirm.setDisable(true);
        try {
            Result<User> result = userService.userInfo();
            userProperty().set(result.getData());
            usernameTextField.setText(result.getData().getUsername());
            nicknameTextField.setText(result.getData().getNickname());
            emailTextField.setText(result.getData().getEmail());
        } catch (IOException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
        nicknameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                confirm.setDisable(false);
            }
        });
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                confirm.setDisable(false);
            }
        });
//        usernameTextField.textProperty().bindBidirectional(userProperty(), new StringConverter<User>() {
//            @Override
//            public String toString(User object) {
//                if (object == null) {
//                    return null;
//                }
//                return object.getUsername();
//            }
//
//            @Override
//            public User fromString(String string) {
//                if (userProperty().get() == null) {
//                    userProperty().set(new User());
//                }
//                userProperty().get().setUsername(string);
//                return userProperty().get();
//            }
//        });
//        nicknameTextField.textProperty().bindBidirectional(userProperty(), new StringConverter<User>() {
//            @Override
//            public String toString(User object) {
//                if (object == null) {
//                    return null;
//                }
//                return object.getNickname();
//            }
//
//            @Override
//            public User fromString(String string) {
//                if (userProperty().get() == null) {
//                    userProperty().set(new User());
//                }
//                userProperty().get().setNickname(string);
//                return userProperty().get();
//            }
//        });
//        emailTextField.textProperty().bindBidirectional(userProperty(), new StringConverter<User>() {
//            @Override
//            public String toString(User object) {
//                if (object == null) {
//                    return null;
//                }
//                return object.getEmail();
//            }
//
//            @Override
//            public User fromString(String string) {
//                if (userProperty().get() == null) {
//                    userProperty().set(new User());
//                }
//                userProperty().get().setEmail(string);
//                return userProperty().get();
//            }
//        });
    }

    @FXML
    private void confirm() {
        if (!StringUtil.hasLength(nicknameTextField.getText()) || !StringUtil.hasLength(emailTextField.getText())) {
            return;
        }
        try {
            userService.update(userProperty().get().getId(), nicknameTextField.getText(), emailTextField.getText());
        } catch (IOException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private UserService userService = new UserService();
    private ObjectProperty<User> user;

    public ObjectProperty<User> userProperty() {
        if (user == null) {
            user = new SimpleObjectProperty<>();
        }
        return user;
    }

    public User getUser() {
        return userProperty().get();
    }

    public void setUser(User user) {
        userProperty().set(user);
    }
}
