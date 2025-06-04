package daisukeclinic.model;

public class Patient extends Person implements Comparable<Patient> {
    private int id;
    private String name;
    private int age;
    private String address;
    private String phoneNumber;

    public Patient(int id, String name, int age, String address, String phoneNumber) {
        super(id, name);
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public int compareTo(Patient other) {
        if (isCompareId() || other.isCompareId()) {
            return Integer.compare(id, other.id);
        }
        return name.compareTo(other.getName());
    }
}
