package daisukeclinic.view;

import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.SaveUtility;
import daisukeclinic.utils.TableUtility;

import java.time.LocalDateTime;
import java.time.LocalTime;

import daisukeclinic.components.MenuItem;
import daisukeclinic.components.MenuList;
import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.AppointmentQueue;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.DoctorLoginList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.datastructure.Stack;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.MedicalRecord;
import daisukeclinic.model.User;
import daisukeclinic.routes.LockScreen;
import daisukeclinic.routes.PatientRoute;

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
        // menuStack.push(mainMenuList);

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

        // ========== LOGIN MENU ==========

        loginMenuList.addMenuItem(new MenuItem("Login", () -> LockScreen.loginAccountPage()));
        loginMenuList.addMenuItem(new MenuItem("Register", () -> LockScreen.registerAccountPage()));
        loginMenuList.addMenuItem(new MenuItem("Guest Mode", null));
        loginMenuList.addMenuItem(new MenuItem("Quit", () -> {
            isRunning = false;
        }));

        // ========== END LOGIN MENU ==========

        // ========== MAIN MENU ==========

        mainMenuList.addMenuItem(new MenuItem("Manage Patients", () -> menuStack.push(managePatientMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Doctors", () -> menuStack.push(manageDoctorMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Appointments", () -> menuStack.push(manageAppointmentMenuList)));
        mainMenuList.addMenuItem(new MenuItem("About This App", () -> {

        }));
        mainMenuList.addMenuItem(new MenuItem("Exit", () -> menuStack.pop()));

        // ========== END MAIN MENU ==========

        // ========== MANAGE PATIENTS ==========

        managePatientMenuList.addMenuItem(new MenuItem("Add New Patient", () -> PatientRoute.addNewPatientRoute()));
        managePatientMenuList.addMenuItem(new MenuItem("Remove Patient", () -> PatientRoute.removePatientRoute()));
        managePatientMenuList.addMenuItem(new MenuItem("Search Patient", () -> PatientRoute.searchPatientRoute()));
        managePatientMenuList
                .addMenuItem(new MenuItem("View Medical Records", () -> PatientRoute.viewMedicalRecordsRoute()));
        managePatientMenuList
                .addMenuItem(new MenuItem("Display All Patients", () -> PatientRoute.displayAllPatientsRoute()));
        managePatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

        // ========== END MANAGE PATIENTS ==========

        // ========== START MANAGE DOCTORS ==========

        // Register new doctor
        manageDoctorMenuList.addMenuItem(new MenuItem("Register New Doctor", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Register New Doctor");

            String name = ConsoleUtility.getStringPromptInput("Name: ");
            String specialty = ConsoleUtility.getStringPromptInput("Specialty: ");

            LocalTime scheduleStart = ConsoleUtility.getLocalTimePromptInput("Schedule Starts  At: ",
                    LocalTime.of(8, 0), LocalTime.of(18, 0));
            LocalTime scheduleEnd = ConsoleUtility.getLocalTimePromptInput("Schedule Ends  At: ", scheduleStart,
                    LocalTime.of(19, 0));

            DoctorList.getInstance().registerDoctor(name, specialty, scheduleStart, scheduleEnd);

            System.out.println("Doctor is successfuly added! . . .\n");

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Login doctor
        manageDoctorMenuList.addMenuItem(new MenuItem("Login Doctor", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Login Doctor");

            int doctorId = ConsoleUtility.getIntPromptInput("Doctor ID: ");
            Doctor doctor = DoctorList.getInstance().findDoctorById(doctorId);
            if (doctor != null) {
                if (DoctorLoginList.getInstance().loginDoctor(doctor)) {
                    LinkedList<Doctor> lTmp = new LinkedList<>();
                    lTmp.insertBack(doctor);
                    System.out.println("Doctor logged in:");
                    TableUtility.displayDoctorLoginTable(lTmp);
                    System.out.println("Successfully logged in!");
                } else {
                    System.out.println("Doctor already logged in!");
                }
            } else {
                System.out.println("Can't find a Doctor with ID: " + doctorId + "!");
            }

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Logout doctor
        manageDoctorMenuList.addMenuItem(new MenuItem("Logout Doctor", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Logout Doctor");

            int doctorId = ConsoleUtility.getIntPromptInput("Doctor ID: ");
            Doctor doctor = DoctorList.getInstance().findDoctorById(doctorId);
            if (doctor != null) {
                if (DoctorLoginList.getInstance().logoutDoctor(doctorId)) {
                    LinkedList<Doctor> lTmp = new LinkedList<>();
                    lTmp.insertBack(doctor);
                    System.out.println("Doctor logged out:");
                    TableUtility.displayDoctorLoginTable(lTmp);
                    System.out.println("Successfully logged out in!");
                } else {
                    System.out.println("Doctor is not logged in!");
                }
            } else {
                System.out.println("Can't find a Doctor with ID: " + doctorId + "!");
            }

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // All doctors
        manageDoctorMenuList.addMenuItem(new MenuItem("View All Doctors In This Clinic", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("All Doctors In Daisuke Clinic");

            DoctorList.getInstance().displayAllDoctors();

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Last logged in doctors
        manageDoctorMenuList.addMenuItem(new MenuItem("View Last Logged In Doctors", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Last Logged In Doctors");

            DoctorLoginList.getInstance().getAllLoggedInDoctors();

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Back to main menu
        manageDoctorMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

        // ========== END MANAGE DOCTORS ==========

        // ========== START MANAGE APPOINTMENT ==========

        // Schedule Appointment
        manageAppointmentMenuList.addMenuItem(new MenuItem("Schedule Appointment", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Schedule Appointment");

            int patientId = 0;
            while (true) {
                patientId = ConsoleUtility.getIntPromptInput("Patient's Id: ");
                if (PatientRecord.getInstance().findPatientById(patientId) == null) {
                    System.out.println("Patient with that ID does not exist!");
                    continue;
                }
                break;
            }

            int doctorId = 0;
            while (true) {
                doctorId = ConsoleUtility.getIntPromptInput("Doctor's Id: ");
                if (DoctorList.getInstance().findDoctorById(doctorId) == null) {
                    System.out.println("Doctor with that ID does not exist!");
                    continue;
                }
                break;
            }

            LocalDateTime appointmentTime = ConsoleUtility.getDateTimePromptInput("Date & Time: ");

            // System.out.println("Appointment Time");

            AppointmentManager.getInstance().scheduleAppointment(patientId, doctorId, appointmentTime);

            System.out.println("Successfuly scheduled the appointment!\n");

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Process Appointment
        manageAppointmentMenuList.addMenuItem(new MenuItem("Proccess Appointment", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Proccess Appointment");

            int doctorId = ConsoleUtility.getIntPromptInput("Doctor ID: ");

            Doctor doctor = DoctorList.getInstance().findDoctorById(doctorId);
            if (doctor != null) {

                if (doctor.isLoggedIn()) {

                    System.out.println(AppointmentManager.getInstance().getAppointments().isPresent(doctorId));

                    Appointment appointment = AppointmentManager.getInstance().proccessNextAppointment(doctorId);
                    if (appointment != null) {
                        LinkedList<Appointment> appointmentList = new LinkedList<>();
                        appointmentList.insertBack(appointment);
                        TableUtility.displayAppointmentTable(appointmentList);

                        String patientProblem = ConsoleUtility.getStringPromptInput("Patient's Problem: ");
                        String patientDiagnosis = ConsoleUtility.getStringPromptInput("Patient's Diagnosis: ");
                        String patientDrug = ConsoleUtility.getStringPromptInput("Patient's Drug: ");

                        PatientRecord.getInstance().findPatientById(appointment.getPatientId()).getMedicalRecords()
                                .insertBack(
                                        new MedicalRecord(doctorId, appointment.getTime(), patientProblem,
                                                patientDiagnosis, patientDrug));

                        System.out.println("\nAppointment proccessed, get well soon!\n");
                    } else {
                        System.out.println("\nThis doctor currently has no more appointments!.\n");
                    }
                } else {
                    System.out.println(
                            "Can't proccess appointment because this doctor isn't currently available this time.");
                }
            } else {
                System.out.println("Can't find doctor with that ID!");
            }

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Cancel Appointment
        manageAppointmentMenuList.addMenuItem(new MenuItem("Cancel Appointment", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Cancel Appointment");

            int doctorId = ConsoleUtility.getIntPromptInput("Enter doctor ID: ");
            AppointmentQueue appointmentQueue = AppointmentManager.getInstance().getAppointmentQueue(doctorId);

            if (appointmentQueue != null) {
                ConsoleUtility.clearScreen();
                ConsoleUtility.printTitle("Select Appointment to Cancel");
                TableUtility.displayAppointmentTable(appointmentQueue.getQueue());
                int appointmentId = ConsoleUtility.getIntPromptInput("Enter Appointment ID: ");
                Appointment appointment = appointmentQueue.getQueue()
                        .find(new Appointment(appointmentId, 0, 0, null));
                if (appointment != null) {
                    LinkedList<Appointment> deletedAppointments = new LinkedList<>();
                    deletedAppointments.insertBack(appointment);
                    TableUtility.displayAppointmentTable(deletedAppointments);
                    appointmentQueue.getQueue().remove(appointment);
                    System.out.println("Appointment has been successfuly canceled!\n");
                } else {
                    System.out.println("Unable to find the appointment\n");
                }
            } else {
                System.out.println("Unable to find the doctor\n");
            }

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // View Upcoming Appointments
        manageAppointmentMenuList.addMenuItem(new MenuItem("View Upcoming Appointments", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Upcoming Appointments");

            AppointmentManager.getInstance().viewUpcomingAppointments();

            ConsoleUtility.pressAnyKeyToContinue();

        }));
        manageAppointmentMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

        // ========== END MANAGE APPOINTMENT ==========

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

    public static void patientUserMenuSetup() {

    }
}
