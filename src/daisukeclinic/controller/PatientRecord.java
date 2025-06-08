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
        Patient newPatient = new Patient(lastId++, name, age, address, phoneNumber);
        patients.insertBack(newPatient);
        SearchablePatientTree.getInstance().insertPatient(newPatient);
    }

    public boolean removePatientById(int id) {
        Patient decoy = new Patient(id, null, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);

        if (patients.find(decoy) == null) {
            return false;
        }

        patients.remove(decoy);
        return true;
    }

    public Patient findPatientById(int id) {
        Patient decoy = new Patient(id, null, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);
        return patients.find(decoy);
    }

    public Patient findPatientByName(String name) {
        Patient decoy = new Patient(0, name, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_NAME);
        return patients.find(decoy);
    }

    // TODO:
    public boolean findPatientsByNameContaining(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return false;
        }

        // Escape special regex characters in the search term
        String escapedSearchTerm = searchTerm.replaceAll("[\\W]", "\\\\$0");
        String regex = "(?i).*" + escapedSearchTerm + ".*"; // Case-insensitive pattern
        int found = 0;

        for (int i = 0; i < patients.getSize(); i++) {
            Patient patient = patients.getIndex(i);
            if (patient.getName() != null && patient.getName().matches(regex)) {
                System.out.println(patient);
                found++;
            }
        }

        if (found > 0)
            return true;
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

    public LinkedList<Patient> getList() {
        return patients;
    }

    public Patient get(int index) {
        return patients.getIndex(index);
    }

    public int getPatientCount() {
        return patients.getSize();
    }
}
