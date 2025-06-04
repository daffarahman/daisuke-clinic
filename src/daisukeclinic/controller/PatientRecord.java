package daisukeclinic.controller;

import daisukeclinic.model.Patient;
import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.model.Person;

public class PatientRecord {
    private LinkedList<Patient> patients;
    private static PatientRecord instance;
    private int lastId = 0;

    private PatientRecord() {
        patients = new LinkedList<>();
    }

    public static PatientRecord getInstance() {
        if (instance == null) {
            instance = new PatientRecord();
        }
        return instance;
    }

    public void addPatient(String name, int age, String address, String phoneNumber) {
        patients.insertBack(new Patient(lastId++, name, age, address, phoneNumber));
    }

    public void removePatientById(int id) {
        Patient decoy = new Patient(id, null, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);
        patients.remove(decoy);
    }

    public boolean findPatientByName(String name) {
        Patient decoy = new Patient(0, name, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_NAME);
        if (patients.find(decoy) != null) {
            return true;
        }
        return false;
    }

    public void displayAllPatients() {
        System.out.printf("%-5s %-20s %-3s %-30s %-15s%n", "ID", "Name", "Age", "Address", "Phone Number");
        System.out.println("-------------------------------------------------------------------------------------");
        for (int i = 0; i < patients.getSize(); i++) {
            Patient patient = patients.getIndex(i);
            System.out.printf("%-5d %-20s %-3d %-30s %-15s%n",
                    patient.getId(), patient.getName(), patient.getAge(),
                    patient.getAddress(), patient.getPhoneNumber());
        }
    }

    public Patient get(int index) {
        return patients.getIndex(index);
    }

    public int getPatientCount() {
        return patients.getSize();
    }
}
