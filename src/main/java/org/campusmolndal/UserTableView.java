package org.campusmolndal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.campusmolndal.models.UserDto;
import org.campusmolndal.services.LoginService;

import java.util.List;

public class UserTableView {
    private TableView<UserDto> tableView;
    private ObservableList<UserDto> data;

    public UserTableView() {
        // Initiera TableView
        tableView = new TableView<>();

        // Definiera kolumner
        TableColumn<UserDto, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserDto, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserDto, String> authoritiesCol = new TableColumn<>("Authorities");
        authoritiesCol.setCellValueFactory(new PropertyValueFactory<>("authorities"));

        // Lägg till kolumner i TableView
        tableView.getColumns().addAll(idCol, usernameCol, authoritiesCol);

        // Initiera ObservableList
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
    }

    public TableView<UserDto> getTableView() {
        return tableView;
    }

    public void loadDataAsync() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ApiResponse response = LoginService.getAllUsers();
                if (response.getStatusCode() == 200) {
                    List<UserDto> users = response.getUsers();
                    Platform.runLater(() -> data.setAll(users));
                }
                return null;
            }
        };
        new Thread(task).start();
    }
}
