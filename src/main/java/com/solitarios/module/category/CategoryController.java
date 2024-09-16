package com.solitarios.module.category;

import com.solitarios.bean.Category;
import com.solitarios.bean.Result;
import com.solitarios.global.GlobalData;
import com.solitarios.module.ManageCallback;
import com.solitarios.module.MessageBoxController;
import com.solitarios.module.SerialNumberCallback;
import com.solitarios.service.CategoryService;
import com.solitarios.utils.Alerts;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    @FXML
    private TableView<Category> tableView;
    @FXML
    private TableColumn orderColumn;
    @FXML
    private TableColumn<Category, String> nameColumn;
    @FXML
    private TableColumn<Category, String> aliasColumn;
    @FXML
    private TableColumn manageColumn;

    private ManageCallback manageCallback = new ManageCallback();
    private CategoryService categoryService = new CategoryService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                double lastWidth = newValue.doubleValue() - orderColumn.getPrefWidth() - manageColumn.getPrefWidth();
                nameColumn.setPrefWidth(lastWidth * 0.5);
                aliasColumn.setPrefWidth(lastWidth * 0.5);
            }
        });
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderColumn.setCellFactory(new SerialNumberCallback());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("categoryAlias"));
        manageCallback.setCallEditOnMouseClicked(
                param -> event ->
                        CategoryUpdateController.show((Stage) tableView.getScene().getWindow(), (Category) param,
                                actionEvent -> {
                                    try {
                                        categoryService.update(((Category) param).getId(),
                                                ((Category) param).getCategoryName(),
                                                ((Category) param).getCategoryAlias());
                                    } catch (IOException e) {
                                        Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                                        throw new RuntimeException(e);
                                    }
                                    loadData();
                                }));
        manageCallback.setCallDeleteOnMouseClicked(param -> event ->
                MessageBoxController.show((Stage) tableView.getScene().getWindow(), "温馨提示",
                        "你确认要删除该分类信息吗?？", actionEvent -> {
                            try {
                                categoryService.delete(((Category) param).getId());
                            } catch (IOException e) {
                                Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                                throw new RuntimeException(e);
                            }
                            loadData();
                        }));
        manageColumn.setCellFactory(manageCallback);
        loadData();
    }

    private void loadData() {
        try {
            Result<List<Category>> result = categoryService.list();
            if (result.getCode() == Result.SUCCESS) {
                List<Category> list = result.getData();
                tableView.setItems(FXCollections.observableArrayList(list));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addCategory() {
        Category category = new Category();
        CategoryUpdateController.show((Stage) tableView.getScene().getWindow(), category,
                actionEvent -> {
                    try {
                        categoryService.add(category.getCategoryName(),
                                category.getCategoryAlias());
                    } catch (IOException e) {
                        Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                        throw new RuntimeException(e);
                    }
                    loadData();
                });
    }
}
