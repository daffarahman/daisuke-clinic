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
import daisukeclinic.controller.SearchablePatientTree;
import daisukeclinic.controller.UserListManager;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.MedicalRecord;
import daisukeclinic.model.Patient;
import daisukeclinic.model.User;
import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.model.datastructure.Stack;

public class AppConsole {
    private MenuList loginMenuList;
    private MenuList mainMenuList;

    private MenuList managePatientMenuList;
    private MenuList manageDoctorMenuList;
    private MenuList manageAppointmentMenuList;

    private MenuList patientMainMenuList;
    private MenuList doctorMainMenuList;

    private Stack<MenuList> menuStack;

    private User currentUser;
    private boolean isRunning = true;

    public AppConsole() {
        menuStack = new Stack<>();

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

    public void setupMenus() {
        loginMenuList = new MenuList("Daisuke Login", 5);
        mainMenuList = new MenuList("Daisuke Clinic Data Management App", 5);

        managePatientMenuList = new MenuList("Manage Patient", 6);
        manageDoctorMenuList = new MenuList("Manage Doctor", 6);
        manageAppointmentMenuList = new MenuList("Manage Appointment", 5);

        patientMainMenuList = new MenuList("MyDaisuke Patient", 7);
        doctorMainMenuList = new MenuList("MyDaisuke Doctor", 5);

        // ========== LOGIN MENU ==========

        loginMenuList.addMenuItem(new MenuItem("Login", () -> loginAccountScreen()));
        loginMenuList.addMenuItem(new MenuItem("Register", () -> registerAccountScreen()));
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

        // Add Patient
        managePatientMenuList.addMenuItem(new MenuItem("Add New Patient", () -> {
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
        }));

        // Remove Patient
        managePatientMenuList.addMenuItem(new MenuItem("Remove Patient", () -> {
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
            removePatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(removePatientMenuList);
        }));

        // Search Patient
        managePatientMenuList.addMenuItem(new MenuItem("Search Patient", () -> {
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
            searchPatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));
            menuStack.push(searchPatientMenuList);
        }));

        // View medical records
        managePatientMenuList.addMenuItem(new MenuItem("View Medical Records", () -> {
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
        }));

        // Display All Patients
        managePatientMenuList.addMenuItem(new MenuItem("Display All Patients", () -> {
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
            displayAllPatientMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

            menuStack.push(displayAllPatientMenuList);
        }));

        // Back Menu
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

    // ============== LOGIN ACCOUNT ==============
    public static void loginAccountScreen() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Login MyDaisuke Account");

        String username = ConsoleUtility.getStringPromptInput("Username: ");

        User u = UserListManager.getInstance().getUser(username);
        if (u == null) {
            System.out.println("User '" + username + "' did not exist!");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        String password = ConsoleUtility.getPasswordPromptInput("Password: ");
        if (!UserListManager.getInstance().validateUser(username, password)) {
            System.out.println("Wrong password!");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        System.out.println("Successfully Logged In! " + u.getRole());

        ConsoleUtility.pressAnyKeyToContinue();
    }

    // ============== REGISTER ACCOUNT ==============

    public static void registerAccountScreen() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Register MyDaisuke Account");

        final User.Role[] role = new User.Role[1];
        int roleId;

        MenuList roleMenuList = new MenuList("Select Account Type", 3);
        roleMenuList.addMenuItem(new MenuItem("Patient", () -> {
            role[0] = User.Role.ROLE_PATIENT;
        }));
        roleMenuList.addMenuItem(new MenuItem("Doctor", () -> {
            role[0] = User.Role.ROLE_DOCTOR;
        }));
        roleMenuList.addMenuItem(new MenuItem("Superuser Admin", () -> {
            role[0] = User.Role.ROLE_ADMIN;
        }));

        roleMenuList.printMenu();
        roleMenuList.run(roleMenuList.prompt());

        if (role[0] == null) {
            System.out.println("Please select either of three type, exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        String username = ConsoleUtility.getStringPromptInput("Username: ");

        if (UserListManager.getInstance().getUser(username) != null) {
            System.out.println("Username already exist!");
            ConsoleUtility.pressAnyKeyToContinue();
        }

        if (role[0] == User.Role.ROLE_DOCTOR || role[0] == User.Role.ROLE_PATIENT) {
            String fullName = ConsoleUtility.getStringPromptInput("Full Name: ");

            if (role[0] == User.Role.ROLE_DOCTOR) {
                String specialty = ConsoleUtility.getStringPromptInput("Specialty: ");
                LocalTime scheduleStart = ConsoleUtility.getLocalTimePromptInput(
                        "Schedule Starts At: ",
                        LocalTime.of(8, 0), LocalTime.of(18, 0));
                LocalTime scheduleEnd = ConsoleUtility.getLocalTimePromptInput(
                        "Schedule Ends At: ",
                        LocalTime.of(scheduleStart.getHour() + 1, 0), LocalTime.of(19, 0));

                roleId = DoctorList.getInstance().registerDoctor(fullName, specialty, scheduleStart, scheduleEnd);
            } else {
                int age = ConsoleUtility.getIntPromptInput("Age: ");
                String address = ConsoleUtility.getStringPromptInput("Address: ");
                String phoneNumber = ConsoleUtility.getPhoneNumberPromptInput("Phone Number: ");

                roleId = PatientRecord.getInstance().addPatient(fullName, age, address, phoneNumber);
            }
        } else {
            roleId = -1;
        }

        String password = ConsoleUtility.getPasswordPromptInput("Enter Password: ");

        UserListManager.getInstance().addNewUser(username, password, role[0], roleId);

        ConsoleUtility.pressAnyKeyToContinue();
    }

    // ============== END REGISTER ACCOUNT ==============
}
