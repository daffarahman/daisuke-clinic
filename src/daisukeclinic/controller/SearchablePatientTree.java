package daisukeclinic.controller;

import java.io.Serializable;

import daisukeclinic.datastructure.BST;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Patient;
import daisukeclinic.model.Person;
import daisukeclinic.utils.TableUtility;

public class SearchablePatientTree implements Serializable {
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

    public static void setInstance(SearchablePatientTree newInstance) {
        instance = newInstance;
    }

    public void insertPatient(Patient p) {
        patientTree.insert(p);
    }

    public Patient searchPatient(int id) {
        Patient decoy = new Patient(id, null, 0, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);
        return patientTree.search(decoy);
    }

    public void deletePatientFromTree(Patient decoy) {
        patientTree.delete(decoy);
    }

    public void preOrderDisplay() {
        LinkedList<Patient> preorderList = patientTree.getTraversedList(BST.TraverseMode.TRAVERSE_PREORDER);
        TableUtility.displayPatientTable(preorderList);
    }

    public void inOrderDisplay() {
        LinkedList<Patient> inorderList = patientTree.getTraversedList(BST.TraverseMode.TRAVERSE_INORDER);
        TableUtility.displayPatientTable(inorderList);
    }

    public void postOrderDisplay() {
        LinkedList<Patient> postorderList = patientTree.getTraversedList(BST.TraverseMode.TRAVERSE_POSTORDER);
        TableUtility.displayPatientTable(postorderList);
    }
}