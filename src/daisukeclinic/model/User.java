package daisukeclinic.model;

public class User implements Comparable<User> {
    private String username;
    private String password;
    private Role role;
    private int roleId;
    private CompareMode compareMode;

    public static enum Role {
        ROLE_PATIENT,
        ROLE_DOCTOR,
        ROLE_ADMIN
    }

    public static enum CompareMode {
        COMPARE_BY_USERNAME,
        COMPARE_BY_ROLE,
        COMPARE_BY_ROLE_ID
    }

    public User(String username, String password, Role role, int roleId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.roleId = roleId;
        this.compareMode = CompareMode.COMPARE_BY_USERNAME;
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

    public void setCompareMode(CompareMode mode) {
        this.compareMode = mode;
    }

    public CompareMode getCompareMode() {
        return compareMode;
    }

    @Override
    public int compareTo(User otherUser) {
        if (compareMode == CompareMode.COMPARE_BY_USERNAME
                || otherUser.getCompareMode() == CompareMode.COMPARE_BY_USERNAME)
            return username.compareTo(otherUser.getUsername());
        if (compareMode == CompareMode.COMPARE_BY_ROLE || otherUser.getCompareMode() == CompareMode.COMPARE_BY_ROLE)
            return (role == otherUser.getRole()) ? 0 : -1;
        if (compareMode == CompareMode.COMPARE_BY_ROLE_ID
                || otherUser.getCompareMode() == CompareMode.COMPARE_BY_ROLE_ID)
            return Integer.compare(roleId, otherUser.getRoleId());
        return -1;
    }
}
