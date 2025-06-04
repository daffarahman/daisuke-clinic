package daisukeclinic.view;

import java.util.Scanner;

import daisukeclinic.utils.Utility;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.controller.SearchablePatientTree;
import daisukeclinic.model.Patient;
import daisukeclinic.model.datastructure.Stack;
import daisukeclinic.view.components.console.MenuItem;
import daisukeclinic.view.components.console.MenuList;

public class AppConsole {
    private MenuList mainMenuList;
    private MenuList managePatientMenuList;
    private MenuList manageDoctorMenuList;
    private MenuList manageAppointmentMenuList;

    private Stack<MenuList> menuStack;
    private boolean menuShow = true;

    private Scanner scanner;

    public AppConsole() {
        scanner = new Scanner(System.in);

        menuStack = new Stack<>();

        setupMenus();
        menuStack.push(mainMenuList);

        while (!menuStack.isEmpty()) {
            if (menuShow) {
                Utility.clearScreen();
                menuStack.peek().printMenu();
                menuStack.peek().run(menuStack.peek().prompt());
            }
        }
    }

    public void setupMenus() {
        mainMenuList = new MenuList("Main Menu", 5);
        managePatientMenuList = new MenuList("Manage Patient Menu", 5);
        manageDoctorMenuList = new MenuList("Manage Doctor Menu", 7);
        manageAppointmentMenuList = new MenuList("Manage Appointment Menu", 5);

        mainMenuList.addMenuItem(new MenuItem("Manage Patients", () -> menuStack.push(managePatientMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Doctors", () -> menuStack.push(manageDoctorMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Appointments", () -> menuStack.push(manageAppointmentMenuList)));
        mainMenuList.addMenuItem(new MenuItem("View all data", null));
        mainMenuList.addMenuItem(new MenuItem("Exit", () -> menuStack.pop()));

        // Add Patient
        managePatientMenuList.addMenuItem(new MenuItem("Add New Patient", () -> {
            Utility.clearScreen();
            Utility.printTitle("Add New Patient");
        }));
        managePatientMenuList.addMenuItem(new MenuItem("Remove Patient", () -> {
            MenuList removePatientMenuList = new MenuList("Remove Patient", 2);
            removePatientMenuList.addMenuItem(new MenuItem("Remove By Id", () -> {
                Utility.clearScreen();
                Utility.printTitle("Remove a Patient Their ID");
                System.out.print("Enter Patient ID: ");
                int selectedId = getIntegerInput(-1);
                if (PatientRecord.getInstance().removePatientById(selectedId)) {
                    System.out.printf("Patient with id: %d has been successfuly removed\n", selectedId);
                } else {
                    System.out.printf("Unable to find patient with id: %d!\n", selectedId);
                }
                Utility.pressAnyKeyToContinue();
            }));
            removePatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(removePatientMenuList);
        }));
        managePatientMenuList.addMenuItem(new MenuItem("Search Patient", () -> {
            MenuList searchPatientMenuList = new MenuList("Search Patient", 3);
            searchPatientMenuList.addMenuItem(new MenuItem("Search By Id (BST)", () -> {
                Utility.clearScreen();
                Utility.printTitle("Fast lookup for patient");
                System.out.print("Enter Patient ID: ");
                int selectedId = getIntegerInput(-1);
                Patient foundPatient = SearchablePatientTree.getInstance().searchPatient(selectedId);
                if (foundPatient != null) {
                    System.out.printf("Patient with id: %d found!\n", selectedId);
                    System.out.println(foundPatient.toString());
                } else {
                    System.out.printf("Unable to find patient with id: %d!\n", selectedId);
                }
                Utility.pressAnyKeyToContinue();
            }));
            searchPatientMenuList.addMenuItem(new MenuItem("Search By Name", null));
            searchPatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(searchPatientMenuList);
        }));
        managePatientMenuList.addMenuItem(new MenuItem("Display All Patients", () -> {
            MenuList displayAllPatientMenuList = new MenuList("Display All Patient", 5);
            displayAllPatientMenuList.addMenuItem(new MenuItem("Normal", () -> {
                Utility.clearScreen();
                Utility.printTitle("All Patients in Daisuke Clinic");
                PatientRecord.getInstance().displayAllPatients();
                Utility.pressAnyKeyToContinue();
            }));
            displayAllPatientMenuList.addMenuItem(new MenuItem("Preorder", () -> {
                Utility.clearScreen();
                Utility.printTitle("All Patients in Daisuke Clinic (Preorder)");
                SearchablePatientTree.getInstance().preOrderDisplay();
                Utility.pressAnyKeyToContinue();
            }));
            displayAllPatientMenuList.addMenuItem(new MenuItem("Inorder", () -> {
                Utility.clearScreen();
                Utility.printTitle("All Patients in Daisuke Clinic (Inorder)");
                SearchablePatientTree.getInstance().inOrderDisplay();
                Utility.pressAnyKeyToContinue();
            }));
            displayAllPatientMenuList.addMenuItem(new MenuItem("Postorder", () -> {
                Utility.clearScreen();
                Utility.printTitle("All Patients in Daisuke Clinic (Postorder)");
                SearchablePatientTree.getInstance().postOrderDisplay();
                Utility.pressAnyKeyToContinue();
            }));
            displayAllPatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(displayAllPatientMenuList);
        }));
        managePatientMenuList.addMenuItem(new MenuItem("Back to Main Menu", () -> menuStack.pop()));

        manageDoctorMenuList.addMenuItem(new MenuItem("Register New Doctor", null));
        manageDoctorMenuList.addMenuItem(new MenuItem("Login Doctor", null));
        manageDoctorMenuList.addMenuItem(new MenuItem("Logout Doctor", null));
        manageDoctorMenuList.addMenuItem(new MenuItem("View All Doctors", null));
        manageDoctorMenuList.addMenuItem(new MenuItem("View All Logged In Doctors", null));
        manageDoctorMenuList.addMenuItem(new MenuItem("View Last Logged In Doctors", null));
        manageDoctorMenuList.addMenuItem(new MenuItem("Back to Main Menu", () -> menuStack.pop()));

        manageAppointmentMenuList.addMenuItem(new MenuItem("Schedule Appointment", null));
        manageAppointmentMenuList.addMenuItem(new MenuItem("Proccess Appointment", null));
        manageAppointmentMenuList.addMenuItem(new MenuItem("Cancel Appointment", null));
        manageAppointmentMenuList.addMenuItem(new MenuItem("View Upcoming Appointments", null));
        manageAppointmentMenuList.addMenuItem(new MenuItem("Back to Main Menu", () -> menuStack.pop()));
    }

    private int getIntegerInput(int fallback) {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return fallback;
        } finally {
            scanner.nextLine();
        }
    }
}
