package daisukeclinic.controller;

import java.util.LinkedList;

import daisukeclinic.model.User;

public class UserListManager {
    private LinkedList<User> userList;
    private static UserListManager instance;

    private UserListManager() {
        userList = new LinkedList<>();
    }

    public static UserListManager getInstance() {
        if (instance == null) {
            instance = new UserListManager();
        }
        return instance;
    }
}
