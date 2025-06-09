package daisukeclinic.controller;

import java.io.Serializable;

import daisukeclinic.model.Patient;
import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.utils.TableUtility;
import daisukeclinic.model.Person;

public class PatientRecord implements Serializable {
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

    public LinkedList<Patient> findPatientsByNameContaining(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return null;
        }

        // Escape special regex characters in the search term
        String escapedSearchTerm = searchTerm.replaceAll("[\\W]", "\\\\$0");
        String regex = "(?i).*" + escapedSearchTerm + ".*"; // Case-insensitive pattern
        LinkedList<Patient> found = new LinkedList<>();

        for (int i = 0; i < patients.getSize(); i++) {
            Patient patient = patients.getIndex(i);
            if (patient.getName() != null && patient.getName().matches(regex)) {
                found.insertBack(patient);
            }
        }
        if (found.isEmpty())
            return null;
        return found;
    }

    public void displayAllPatients() {
        TableUtility.displayPatientTable(patients);
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
