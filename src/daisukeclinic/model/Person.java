package daisukeclinic.model;

import java.io.Serializable;

public class Person implements Serializable {
    private int id;
    private String name;
    private CompareMode compareMode = CompareMode.COMPARE_BY_ID;
    private boolean isConnectedToAccount = false;

    public static enum CompareMode {
        COMPARE_BY_ID,
        COMPARE_BY_NAME
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = (name == null) ? "" : name.strip();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.strip();
    }

    public CompareMode getCompareMode() {
        return compareMode;
    }

    public void setCompareMode(CompareMode compareMode) {
        this.compareMode = compareMode;
    }

    public boolean isConnectedToAccount() {
        return isConnectedToAccount;
    }

    public void setIsConnectedAccount(boolean b) {
        isConnectedToAccount = b;
    }

    public int compareWithAnotherPerson(Person other) {
        if (compareMode == CompareMode.COMPARE_BY_NAME || other.compareMode == CompareMode.COMPARE_BY_NAME) {
            return name.toLowerCase().strip().compareTo(other.name.toLowerCase().strip());
        }
        return Integer.compare(id, other.id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
