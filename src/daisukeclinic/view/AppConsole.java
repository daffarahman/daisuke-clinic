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
import daisukeclinic.routes.AdminRoutes;
import daisukeclinic.routes.DoctorRoutes;
import daisukeclinic.routes.GlobalRoutes;
import daisukeclinic.routes.GreeterRoutes;
import daisukeclinic.routes.PatientRoutes;

public class AppConsole {

        private static AppConsole instance;

        // Universal Menu
        public MenuList loginMenuList;
        public MenuList mainMenuList;

        // Menu For Admin
        public MenuList managePatientMenuList;
        public MenuList manageDoctorMenuList;
        public MenuList manageAppointmentMenuList;

        public MenuList patientMainMenuList;
        public MenuList doctorMainMenuList;

        public Stack<MenuList> menuStack;

        public User currentUser;
        public boolean isRunning = true;

        public AppConsole() {
                menuStack = new Stack<>();
                setupMenus();
        }

        public void start() {
                while (isRunning) {
                        SaveUtility.saveAll();
                        SaveUtility.loadAll();

                        ConsoleUtility.clearScreen();

                        if (menuStack.peek() != null) {

                                menuStack.peek().printMenu();

                                if (currentUser.getRole() == User.Role.ROLE_ADMIN) {
                                        int appointmentPending = 0;
                                        for (int i = 0; i < AppointmentManager.getInstance().getAppointments()
                                                        .getEntries()
                                                        .getSize(); i++) {
                                                appointmentPending += AppointmentManager.getInstance().getAppointments()
                                                                .getEntries()
                                                                .getIndex(i).value
                                                                .getQueue().getSize();
                                        }

                                        ConsoleUtility.printTitle(
                                                        String.format("Available Doctors: (%d/%d) | Patients: %d | Appointment Pending: %d",
                                                                        DoctorLoginList.getInstance().getList()
                                                                                        .getSize(),
                                                                        DoctorList.getInstance().getList().getSize(),
                                                                        PatientRecord.getInstance().getList().getSize(),
                                                                        appointmentPending));
                                }

                                menuStack.peek().run(menuStack.peek().prompt());
                        } else {
                                if (currentUser != null)
                                        currentUser = null;

                                loginMenuList.printMenu();
                                loginMenuList.run(loginMenuList.prompt());
                        }
                }
        }

        public static AppConsole getInstance() {
                if (instance == null) {
                        instance = new AppConsole();
                }
                return instance;
        }

        public void setupMenus() {
                loginMenuList = new MenuList("MyDaisuke Login", 4);
                mainMenuList = new MenuList("Daisuke Clinic Data Management App", 6);

                managePatientMenuList = new MenuList("Manage Patient", 6);
                manageDoctorMenuList = new MenuList("Manage Doctor", 6);
                manageAppointmentMenuList = new MenuList("Manage Appointment", 5);

                patientMainMenuList = new MenuList("MyDaisuke Patient", 7);
                doctorMainMenuList = new MenuList("MyDaisuke Doctor", 5);

                // Login Menu
                loginMenuList.addMenuItem(new MenuItem("Login", () -> GreeterRoutes.loginAccountPage()));
                loginMenuList.addMenuItem(new MenuItem("Register", () -> GreeterRoutes.registerAccountPage()));
                loginMenuList.addMenuItem(new MenuItem("Guest Mode", null));
                loginMenuList.addMenuItem(new MenuItem("Quit", () -> {
                        isRunning = false;
                }));

                // Main Menu
                mainMenuList.addMenuItem(new MenuItem("Manage Patients", () -> menuStack.push(managePatientMenuList)));
                mainMenuList.addMenuItem(new MenuItem("Manage Doctors", () -> menuStack.push(manageDoctorMenuList)));
                mainMenuList.addMenuItem(
                                new MenuItem("Manage Appointments", () -> menuStack.push(manageAppointmentMenuList)));
                mainMenuList.addMenuItem(new MenuItem("About This App", () -> {

                }));
                mainMenuList.addMenuItem(
                                new MenuItem("Register & Connect Account",
                                                () -> AdminRoutes.registerAndConnectAccountRoute()));
                mainMenuList.addMenuItem(new MenuItem("Exit", () -> menuStack.pop()));

                // Manage Patients
                managePatientMenuList
                                .addMenuItem(new MenuItem("Add New Patient", () -> AdminRoutes.addNewPatientRoute()));
                managePatientMenuList
                                .addMenuItem(new MenuItem("Remove Patient", () -> AdminRoutes.removePatientRoute()));
                managePatientMenuList
                                .addMenuItem(new MenuItem("Search Patient", () -> AdminRoutes.searchPatientRoute()));
                managePatientMenuList
                                .addMenuItem(new MenuItem("View Medical Records",
                                                () -> AdminRoutes.viewMedicalRecordsRoute()));
                managePatientMenuList
                                .addMenuItem(new MenuItem("Display All Patients",
                                                () -> AdminRoutes.displayAllPatientsRoute()));
                managePatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

                // Manage Doctors
                manageDoctorMenuList.addMenuItem(
                                new MenuItem("Register New Doctor", () -> AdminRoutes.registerNewDoctor()));
                manageDoctorMenuList.addMenuItem(new MenuItem("Login Doctor", () -> AdminRoutes.loginDoctorRoute()));
                manageDoctorMenuList.addMenuItem(new MenuItem("Logout Doctor", () -> AdminRoutes.logoutDoctorRoute()));
                manageDoctorMenuList.addMenuItem(
                                new MenuItem("View All Doctors In This Clinic",
                                                () -> AdminRoutes.viewAllDoctorsInThisClinicRoute()));
                manageDoctorMenuList
                                .addMenuItem(
                                                new MenuItem("View Last Logged In Doctors",
                                                                () -> AdminRoutes.viewLastLoggedInDoctorsRoute()));
                manageDoctorMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
                manageAppointmentMenuList
                                .addMenuItem(new MenuItem("Schedule Appointment",
                                                () -> AdminRoutes.scheduleAppointmentRoute()));
                manageAppointmentMenuList
                                .addMenuItem(new MenuItem("Proccess Appointment",
                                                () -> AdminRoutes.proccessAppointmentRoute()));
                manageAppointmentMenuList
                                .addMenuItem(new MenuItem("Cancel Appointment",
                                                () -> AdminRoutes.cancelAppointmentRoute()));
                manageAppointmentMenuList.addMenuItem(
                                new MenuItem("View Upcoming Appointments",
                                                () -> AdminRoutes.viewUpcomingAppointmentsRoute()));
                manageAppointmentMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

                // ========== PATIENT MAIN MENU ==========
                patientMainMenuList
                                .addMenuItem(new MenuItem("Schedule an Appointment",
                                                () -> PatientRoutes.scheduleAppointmentRoute()));
                patientMainMenuList
                                .addMenuItem(new MenuItem("Cancel an Appointment",
                                                () -> PatientRoutes.cancelAppointmentRoute()));
                patientMainMenuList.addMenuItem(
                                new MenuItem("View My Upcoming Appointments",
                                                () -> PatientRoutes.viewUpcomingAppointmentsRoute()));
                patientMainMenuList.addMenuItem(
                                new MenuItem("View Doctors At Daisuke Clinics",
                                                () -> AdminRoutes.viewAllDoctorsInThisClinicRoute()));
                patientMainMenuList
                                .addMenuItem(new MenuItem("View My Medical Record",
                                                () -> PatientRoutes.viewMedicalRecordsRoute()));
                patientMainMenuList.addMenuItem(new MenuItem("About Me", () -> GlobalRoutes.aboutMeRoute()));
                patientMainMenuList.addMenuItem(new MenuItem("Logout", () -> {
                        currentUser = null;
                        menuStack.pop();
                }));
                // ========== END PATIENT MAIN MENU ==========

                // ========== DOCTOR MAIN MENU ==========
                doctorMainMenuList.addMenuItem(
                                new MenuItem("View Upcoming Appointments",
                                                () -> DoctorRoutes.viewUpcomingAppointmentsRoute()));
                doctorMainMenuList
                                .addMenuItem(new MenuItem("Proccess Appointment",
                                                () -> DoctorRoutes.proccessAppointmentRoute()));
                doctorMainMenuList.addMenuItem(new MenuItem("About Me", () -> GlobalRoutes.aboutMeRoute()));
                doctorMainMenuList.addMenuItem(new MenuItem("Logout", () -> {
                        DoctorList.getInstance().findDoctorById(currentUser.getRoleId()).logout();
                        currentUser = null;
                        menuStack.pop();
                }));
                // ========== END DOCTOR MAIN MENU ==========
        }
}
