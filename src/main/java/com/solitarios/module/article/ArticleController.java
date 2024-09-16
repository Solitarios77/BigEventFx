package com.solitarios.module.article;

import com.solitarios.bean.Article;
import com.solitarios.bean.Category;
import com.solitarios.bean.PageBean;
import com.solitarios.bean.Result;
import com.solitarios.module.ManageCallback;
import com.solitarios.module.MessageBoxController;
import com.solitarios.service.ArticleService;
import com.solitarios.service.CategoryService;
import com.solitarios.utils.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ArticleController implements Initializable {
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private ComboBox<String> stateComboBox;
    @FXML
    private TableView<Article> tableView;
    @FXML
    private TableColumn<Article, String> nameColumn;
    @FXML
    private TableColumn<Article, String> categoryColumn;
    @FXML
    private TableColumn<Article, String> createTimeColumn;
    @FXML
    private TableColumn<Article, String> stateColumn;
    @FXML
    private TableColumn manageColumn;
    @FXML
    private ComboBox<Integer> pageComboBox;
    @FXML
    private Pagination pagination;

    private ManageCallback manageCallback = new ManageCallback();
    private ArticleService articleService = new ArticleService();
    private CategoryService categoryService = new CategoryService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
//        pageComboBox.setConverter(new StringConverter<Integer>() {
//            @Override
//            public String toString(Integer object) {
//                if (object != null) {
//                    return String.format("%d条/页", object.intValue());
//                }
//                return null;
//            }
//
//            @Override
//            public Integer fromString(String string) {
//                return null;
//            }
//        });
//        pageComboBox.getSelectionModel().selectFirst();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(param -> {
            Article article = param.getValue();
            for (Category category : categoryComboBox.getItems()) {
                if (category.getId() == article.getCategoryId()) {
                    return new SimpleStringProperty(category.getCategoryName());
                }
            }
            return new SimpleStringProperty("");
        });
        createTimeColumn.setCellValueFactory(param -> {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createTime = param.getValue().getCreateTime().format(formatter);
            return new SimpleStringProperty(createTime);
        });
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        manageCallback.setCallDeleteOnMouseClicked(param -> event ->
                MessageBoxController.show((Stage) tableView.getScene().getWindow(), "温馨提示",
                        "你确认要删除该文章吗?？", actionEvent -> {
                            try {
                                articleService.delete(((Article) param).getId());
                            } catch (IOException e) {
                                Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                                throw new RuntimeException(e);
                            }
                            loadArticleData();
                        }));
        manageCallback.setCallEditOnMouseClicked(
                param -> event ->
                        ArticleUpdateController.show((Stage) tableView.getScene().getWindow(), (Article) param,
                                categoryComboBox.getItems(),
                                actionEvent -> {
                                    String state = ((Button) actionEvent.getSource()).getText();
                                    try {
                                        articleService.update(((Article) param).getId(),
                                                ((Article) param).getTitle(),
                                                ((Article) param).getContent(),
                                                ((Article) param).getCoverImg(),
                                                state,
                                                ((Article) param).getCategoryId());
                                    } catch (IOException e) {
                                        Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                                        throw new RuntimeException(e);
                                    }
                                    loadArticleData();
                                }));
        manageColumn.setCellFactory(manageCallback);
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                loadArticleData();
                return tableView;
            }
        });
        loadCategoryData();
        loadArticleData();
    }

    private void loadArticleData() {
        Integer categoryId = null;
        Category category = categoryComboBox.getSelectionModel().getSelectedItem();
        if (category != null) {
            categoryId = category.getId();
        }
        String state = stateComboBox.getSelectionModel().getSelectedItem();
        try {
            Result<PageBean<Article>> result = articleService.list(pagination.getCurrentPageIndex() + 1,
                    3, categoryId, state);
            if (result.getCode() == Result.SUCCESS) {
                PageBean<Article> pageBean = result.getData();
                pagination.setPageCount(Math.toIntExact(pageBean.getTotal()));
                tableView.setItems(FXCollections.observableArrayList(pageBean.getItems()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCategoryData() {
        try {
            Result<List<Category>> result = categoryService.list();
            if (result.getCode() == Result.SUCCESS) {
                List<Category> list = result.getData();
                categoryComboBox.setItems(FXCollections.observableArrayList(list));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addArticle() {
        Article article = new Article();
        ArticleUpdateController.show((Stage) tableView.getScene().getWindow(), article,
                categoryComboBox.getItems(),
                actionEvent -> {
                    String state = ((Button) actionEvent.getSource()).getText();
                    try {
                        articleService.add(article.getTitle(),
                                article.getContent(),
                                article.getCoverImg(),
                                state,
                                article.getCategoryId());
                    } catch (IOException e) {
                        Alerts.showAlert(Alert.AlertType.ERROR, e.getMessage());
                        throw new RuntimeException(e);
                    }
                    loadArticleData();
                });
    }

    @FXML
    private void reset() {
        categoryComboBox.getSelectionModel().clearSelection();
        stateComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void search() {
        loadArticleData();
    }
}
