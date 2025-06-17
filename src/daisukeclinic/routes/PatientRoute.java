package daisukeclinic.routes;

import daisukeclinic.components.MenuItem;
import daisukeclinic.components.MenuList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.controller.SearchablePatientTree;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Patient;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;
import daisukeclinic.view.AppConsole;

public class PatientRoute {
    public static void addNewPatientRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Add New Patient");

        String name = ConsoleUtility.getStringPromptInput("Patient's Name: ");
        int age = ConsoleUtility.getIntPromptInput("Patient's Age: ");
        String address = ConsoleUtility.getStringPromptInput("Patient's Address: ");
        String phoneNumber = ConsoleUtility.getPhoneNumberPromptInput("Patient's Phone Number: ");

        PatientRecord.getInstance().addPatient(name, age, address, phoneNumber);

        System.out.println("\nNew patient successfuly added!");

        Patient addedPatient = PatientRecord.getInstance().findPatientByName(name);
        if (addedPatient != null) {
            LinkedList<Patient> l = new LinkedList<>();
            l.insertBack(addedPatient);
            TableUtility.displayPatientTable(l);
        }

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void removePatientRoute() {
        MenuList removePatientMenuList = new MenuList("Remove Patient", 2);
        removePatientMenuList.addMenuItem(new MenuItem("Remove By Id", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Remove a Patient Their ID");
            System.out.print("Enter Patient ID: ");
            int selectedId = ConsoleUtility.getIntPromptInput("Enter Patient ID: ");
            if (PatientRecord.getInstance().removePatientById(selectedId)) {
                System.out.printf("Patient with id: %d has been successfuly removed\n", selectedId);
            } else {
                System.out.printf("Unable to find patient with id: %d!\n", selectedId);
            }
            ConsoleUtility.pressAnyKeyToContinue();
        }));
        removePatientMenuList.addMenuItem(new MenuItem("Back", () -> AppConsole.getMenuStack().pop()));
        AppConsole.getMenuStack().push(removePatientMenuList);
    }

    public static void searchPatientRoute() {
        MenuList searchPatientMenuList = new MenuList("Search Patient", 3);

        // Search Patient by Id (BST)
        searchPatientMenuList.addMenuItem(new MenuItem("Search By Id (BST)", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Fast lookup for patient");
            int selectedId = ConsoleUtility.getIntPromptInput("Enter Patient ID: ");
            Patient foundPatient = SearchablePatientTree.getInstance().searchPatient(selectedId);
            if (foundPatient != null) {
                LinkedList<Patient> l = new LinkedList<>();
                l.insertBack(foundPatient);
                TableUtility.displayPatientTable(l);
            }
            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Search Patient by Name
        searchPatientMenuList.addMenuItem(new MenuItem("Search By Name", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Search Patient By Name");

            String name = ConsoleUtility.getStringPromptInput("Patient Name: ");
            TableUtility.displayPatientTable(PatientRecord.getInstance().findPatientsByNameContaining(name));

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Back Menu
        searchPatientMenuList.addMenuItem(new MenuItem("Back", () -> AppConsole.getMenuStack().pop()));
        AppConsole.getMenuStack().push(searchPatientMenuList);
    }

    public static void viewMedicalRecordsRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("View Patient's Medical Record");

        int patientId = ConsoleUtility.getIntPromptInput("Enter Patient ID: ");
        Patient patient = PatientRecord.getInstance().findPatientById(patientId);
        if (patient != null) {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle(String.format("%s's Medical Record", patient.getName()));
            TableUtility.displayMedicalRecordsTable(patient.getMedicalRecords());
        } else {
            System.out.println("Patient with ID: " + patientId + " does not exist!\n");
        }

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void displayAllPatientsRoute() {
        MenuList displayAllPatientMenuList = new MenuList("Display All Patient", 5);

        // Normal
        displayAllPatientMenuList.addMenuItem(new MenuItem("Normal", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("All Patients in Daisuke Clinic");
            PatientRecord.getInstance().displayAllPatients();
            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Preorder
        displayAllPatientMenuList.addMenuItem(new MenuItem("Preorder", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("All Patients in Daisuke Clinic (Preorder)");
            SearchablePatientTree.getInstance().preOrderDisplay();
            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Inorder
        displayAllPatientMenuList.addMenuItem(new MenuItem("Inorder", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("All Patients in Daisuke Clinic (Inorder)");
            SearchablePatientTree.getInstance().inOrderDisplay();
            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Postorder
        displayAllPatientMenuList.addMenuItem(new MenuItem("Postorder", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("All Patients in Daisuke Clinic (Postorder)");
            SearchablePatientTree.getInstance().postOrderDisplay();
            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Back menu
        displayAllPatientMenuList.addMenuItem(new MenuItem("Back", () -> AppConsole.getMenuStack().pop()));

        AppConsole.getMenuStack().push(displayAllPatientMenuList);
    }
}
