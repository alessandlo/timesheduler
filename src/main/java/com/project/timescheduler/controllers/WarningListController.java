package com.project.timescheduler.controllers;

import com.project.timescheduler.Main;
import com.project.timescheduler.services.Event;
import com.project.timescheduler.services.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class WarningListController {

    @FXML
    private ListView<String> listview;

    String selectedUser;

    private OnActionListener listener;

    public interface OnActionListener {
        void onAction();
    }

    public void initialize(OnActionListener listener, String selectedUser) {
        this.listener = listener;
        this.selectedUser = selectedUser;
        ArrayList<Event> list = new User(selectedUser).getHostedEvents();
        ObservableList<String> item = FXCollections.observableArrayList();

        for (Event e : list) {
            item.add(e.getName());
        }

        listview.setItems(item);
    }

    @FXML
    private void confirmDelete(){
        String delete_events = String.format("DELETE FROM sched_event WHERE creator_name='%s'", selectedUser);
        Main.connection.update(delete_events);
        String delete_user = String.format("DELETE FROM sched_user WHERE username='%s'", selectedUser);
        Main.connection.update(delete_user);

        listener.onAction();
    }

    @FXML
    private void cancelDelete(){
        listener.onAction();
    }
}
