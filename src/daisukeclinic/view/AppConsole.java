package daisukeclinic.view;

import daisukeclinic.utils.Utility;
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

    public AppConsole() {
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
            removePatientMenuList.addMenuItem(new MenuItem("Remove By Id", null));
            removePatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(removePatientMenuList);
        }));
        managePatientMenuList.addMenuItem(new MenuItem("Search Patient", () -> {
            MenuList searchPatientMenuList = new MenuList("Search Patient", 3);
            searchPatientMenuList.addMenuItem(new MenuItem("Search By Id (BST)", null));
            searchPatientMenuList.addMenuItem(new MenuItem("Search By Name", null));
            searchPatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(searchPatientMenuList);
        }));
        managePatientMenuList.addMenuItem(new MenuItem("Display All Patients", () -> {
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
}
