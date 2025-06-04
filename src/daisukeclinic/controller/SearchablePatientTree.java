package daisukeclinic.controller;

import daisukeclinic.model.Patient;
import daisukeclinic.model.Person;
import daisukeclinic.model.datastructure.BST;

public class SearchablePatientTree {
    private static SearchablePatientTree instance;
    private BST<Patient> patientTree;

    public SearchablePatientTree() {
        patientTree = new BST<>();
    }

    public static SearchablePatientTree getInstance() {
        if (instance == null) {
            instance = new SearchablePatientTree();
        }
        return instance;
    }

    public void insertPatient(Patient p) {
        patientTree.insert(p);
    }

    public Patient searchPatient(int id) {
        Patient decoy = new Patient(id, null, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);
        return patientTree.search(decoy);
    }

    public void preOrderDisplay() {
        patientTree.display(BST.TraverseMode.TRAVERSE_PREORDER);
    }

    public void inOrderDisplay() {
        patientTree.display(BST.TraverseMode.TRAVERSE_INORDER);
    }

    public void postOrderDisplay() {
        patientTree.display(BST.TraverseMode.TRAVERSE_POSTORDER);
    }
}