package com.solitarios.module;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;


public class ManageCallback implements Callback<TableColumn, TableCell> {
    private Callback<Object, EventHandler<MouseEvent>> callEditOnMouseClicked = param -> event -> System.out.println("event = " + event);
    private Callback<Object, EventHandler<MouseEvent>> callDeleteOnMouseClicked = param -> event -> System.out.println("event = " + event);

    public void setCallEditOnMouseClicked(Callback<Object, EventHandler<MouseEvent>> callback) {
        this.callEditOnMouseClicked = callback;
    }

    public Callback<Object, EventHandler<MouseEvent>> getCallEditOnMouseClicked() {
        return callEditOnMouseClicked;
    }

    public Callback<Object, EventHandler<MouseEvent>> getCallDeleteOnMouseClicked() {
        return callDeleteOnMouseClicked;
    }

    public void setCallDeleteOnMouseClicked(Callback<Object, EventHandler<MouseEvent>> callDeleteOnMouseClicked) {
        this.callDeleteOnMouseClicked = callDeleteOnMouseClicked;
    }

    @Override
    public TableCell call(TableColumn param) {
        TableCell tableCell = new TableCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    HBox hBox = new HBox();
                    Region editRegion = new Region();
                    StackPane editPane = new StackPane();
                    editPane.getStyleClass().add("edit-pane");
                    editPane.getChildren().add(editRegion);
                    Region deleteRegion = new Region();
                    editPane.setOnMouseClicked(callEditOnMouseClicked.call(getTableView()
                            .getItems().get(getIndex())));
                    StackPane deletePane = new StackPane();
                    deletePane.getStyleClass().add("delete-pane");
                    deletePane.getChildren().add(deleteRegion);
                    deletePane.setOnMouseClicked(callDeleteOnMouseClicked.call(getTableView()
                            .getItems().get(getIndex())));
                    hBox.getChildren().addAll(editPane, deletePane);
                    hBox.getStyleClass().add("manage-box");
                    this.setGraphic(hBox);
                }
            }
        };
        return tableCell;
    }
}
