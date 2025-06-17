package daisukeclinic.model;

import daisukeclinic.datastructure.LinkedList;

public class Patient extends Person implements Comparable<Patient> {
    private int age;
    private String address;
    private String phoneNumber;
    private LinkedList<MedicalRecord> medicalRecords;

    public Patient(int id, String name, int age, String address, String phoneNumber) {
        super(id, name);
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.medicalRecords = new LinkedList<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LinkedList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public int compareTo(Patient other) {
        return compareWithAnotherPerson(other);
    }
}
