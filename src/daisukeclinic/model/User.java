package daisukeclinic.model;

public class User implements Comparable<User> {
    private String username;
    private String password;
    private Role role;
    private int roleId;

    public static enum Role {
        ROLE_PATIENT,
        ROLE_DOCTOR,
        ROLE_ADMIN
    }

    public User(String username, String password, Role role, int roleId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public int compareTo(User otherUser) {
        return this.username.compareTo(otherUser.getUsername());
    }
}
