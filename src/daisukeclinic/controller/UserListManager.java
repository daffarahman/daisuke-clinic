package daisukeclinic.controller;

import daisukeclinic.model.User;
import daisukeclinic.model.User.Role;
import daisukeclinic.model.datastructure.LinkedList;

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

    public static void setInstance(UserListManager newInstance) {
        instance = newInstance;
    }

    public LinkedList<User> getList() {
        return userList;
    }

    public User getUser(String username) {
        return userList.find(new User(username, null, null, 0));
    }

    public void addNewUser(String username, String password, Role role, int roleId) {
        User u = new User(username, password, role, roleId);
        if (userList.find(u) == null)
            userList.insertBack(u);
    }

    public boolean validateUser(String username, String password) {
        User u = userList.find(new User(username, null, null, 0));
        if (u != null) {
            return u.getPassword().equals(password);
        }
        return false;
    }
}
