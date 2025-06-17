package daisukeclinic.view;

import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.SaveUtility;

import daisukeclinic.components.MenuItem;
import daisukeclinic.components.MenuList;
import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.DoctorLoginList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.datastructure.Stack;
import daisukeclinic.model.User;
import daisukeclinic.routes.AppointmentRoutes;
import daisukeclinic.routes.DoctorRoutes;
import daisukeclinic.routes.LockScreen;
import daisukeclinic.routes.PatientRoutes;

public class AppConsole {

    // Universal Menu
    private MenuList loginMenuList;
    private MenuList mainMenuList;

    // Menu For Admin
    private MenuList managePatientMenuList;
    private MenuList manageDoctorMenuList;
    private MenuList manageAppointmentMenuList;

    private MenuList patientMainMenuList;
    private MenuList doctorMainMenuList;

    public static Stack<MenuList> menuStack;

    private User currentUser;
    private boolean isRunning = true;

    public AppConsole() {
        getMenuStack();

        setupMenus();
        menuStack.push(mainMenuList);

        while (isRunning) {
            SaveUtility.saveAll();
            SaveUtility.loadAll();

            ConsoleUtility.clearScreen();

            if (menuStack.peek() != null) {

                menuStack.peek().printMenu();

                int appointmentPending = 0;
                for (int i = 0; i < AppointmentManager.getInstance().getAppointments().getEntries().getSize(); i++) {
                    appointmentPending += AppointmentManager.getInstance().getAppointments().getEntries()
                            .getIndex(i).value
                            .getQueue().getSize();
                }

                ConsoleUtility.printTitle(
                        String.format("Available Doctors: (%d/%d) | Patients: %d | Appointment Pending: %d",
                                DoctorLoginList.getInstance().getList().getSize(),
                                DoctorList.getInstance().getList().getSize(),
                                PatientRecord.getInstance().getList().getSize(),
                                appointmentPending));

                menuStack.peek().run(menuStack.peek().prompt());
            } else {
                if (currentUser != null)
                    currentUser = null;

                loginMenuList.printMenu();
                loginMenuList.run(loginMenuList.prompt());
            }
        }
    }

    public static Stack<MenuList> getMenuStack() {
        if (menuStack == null) {
            menuStack = new Stack<>();
        }
        return menuStack;
    }

    public void setupMenus() {
        loginMenuList = new MenuList("Daisuke Login", 5);
        mainMenuList = new MenuList("Daisuke Clinic Data Management App", 5);

        managePatientMenuList = new MenuList("Manage Patient", 6);
        manageDoctorMenuList = new MenuList("Manage Doctor", 6);
        manageAppointmentMenuList = new MenuList("Manage Appointment", 5);

        patientMainMenuList = new MenuList("MyDaisuke Patient", 7);
        doctorMainMenuList = new MenuList("MyDaisuke Doctor", 5);

        // Login Menu
        loginMenuList.addMenuItem(new MenuItem("Login", () -> LockScreen.loginAccountPage()));
        loginMenuList.addMenuItem(new MenuItem("Register", () -> LockScreen.registerAccountPage()));
        loginMenuList.addMenuItem(new MenuItem("Guest Mode", null));
        loginMenuList.addMenuItem(new MenuItem("Quit", () -> {
            isRunning = false;
        }));

        // Main Menu
        mainMenuList.addMenuItem(new MenuItem("Manage Patients", () -> menuStack.push(managePatientMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Doctors", () -> menuStack.push(manageDoctorMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Appointments", () -> menuStack.push(manageAppointmentMenuList)));
        mainMenuList.addMenuItem(new MenuItem("About This App", () -> {

        }));
        mainMenuList.addMenuItem(new MenuItem("Exit", () -> menuStack.pop()));

        // Manage Patients
        managePatientMenuList.addMenuItem(new MenuItem("Add New Patient", () -> PatientRoutes.addNewPatientRoute()));
        managePatientMenuList.addMenuItem(new MenuItem("Remove Patient", () -> PatientRoutes.removePatientRoute()));
        managePatientMenuList.addMenuItem(new MenuItem("Search Patient", () -> PatientRoutes.searchPatientRoute()));
        managePatientMenuList
                .addMenuItem(new MenuItem("View Medical Records", () -> PatientRoutes.viewMedicalRecordsRoute()));
        managePatientMenuList
                .addMenuItem(new MenuItem("Display All Patients", () -> PatientRoutes.displayAllPatientsRoute()));
        managePatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

        // Manage Doctors
        manageDoctorMenuList.addMenuItem(new MenuItem("Register New Doctor", () -> DoctorRoutes.registerNewDoctor()));
        manageDoctorMenuList.addMenuItem(new MenuItem("Login Doctor", () -> DoctorRoutes.loginDoctorRoute()));
        manageDoctorMenuList.addMenuItem(new MenuItem("Logout Doctor", () -> DoctorRoutes.logoutDoctorRoute()));
        manageDoctorMenuList.addMenuItem(
                new MenuItem("View All Doctors In This Clinic", () -> DoctorRoutes.viewAllDoctorsInThisClinicRoute()));
        manageDoctorMenuList
                .addMenuItem(
                        new MenuItem("View Last Logged In Doctors", () -> DoctorRoutes.viewLastLoggedInDoctorsRoute()));
        manageDoctorMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
        manageAppointmentMenuList
                .addMenuItem(new MenuItem("Schedule Appointment", () -> AppointmentRoutes.scheduleAppointmentRoute()));
        manageAppointmentMenuList
                .addMenuItem(new MenuItem("Proccess Appointment", () -> AppointmentRoutes.proccessAppointmentRoute()));
        manageAppointmentMenuList
                .addMenuItem(new MenuItem("Cancel Appointment", () -> AppointmentRoutes.cancelAppointmentRoute()));
        manageAppointmentMenuList.addMenuItem(
                new MenuItem("View Upcoming Appointments", () -> AppointmentRoutes.viewUpcomingAppointmentsRoute()));
        manageAppointmentMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

        // ========== PATIENT MAIN MENU ==========
        patientMainMenuList.addMenuItem(new MenuItem("Schedule an Appointment", null));
        patientMainMenuList.addMenuItem(new MenuItem("Cancel an Appointment", null));
        patientMainMenuList.addMenuItem(new MenuItem("View My Upcoming Appointments", null));
        patientMainMenuList.addMenuItem(new MenuItem("View Doctor Schedule", null));
        patientMainMenuList.addMenuItem(new MenuItem("View My Medical Record", null));
        patientMainMenuList.addMenuItem(new MenuItem("About Me", null));
        patientMainMenuList.addMenuItem(new MenuItem("Logout", null));
        // ========== END PATIENT MAIN MENU ==========

        // ========== DOCTOR MAIN MENU ==========
        doctorMainMenuList.addMenuItem(new MenuItem("View Appointments", null));
        doctorMainMenuList.addMenuItem(new MenuItem("Proccess Appointment", null));
        doctorMainMenuList.addMenuItem(new MenuItem("About Me", null));
        doctorMainMenuList.addMenuItem(new MenuItem("Logout", null));
        // ========== END DOCTOR MAIN MENU ==========
    }
}
