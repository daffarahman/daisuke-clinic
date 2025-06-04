package daisukeclinic.model;

public class Person {
    private int id;
    private String name;

    private boolean compareId = true;

    public Person(int id, String name) {
        this.id = id;
        this.name = name.strip();
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

    public boolean isCompareId() {
        return compareId;
    }

    public void setCompareId(boolean b) {
        compareId = b;
    }

    public int compareToLocal(Person other) {
        if (compareId || other.isCompareId()) {
            return Integer.compare(id, other.getId());
        }
        return name.toLowerCase().strip().compareTo(other.name.toLowerCase().strip());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
