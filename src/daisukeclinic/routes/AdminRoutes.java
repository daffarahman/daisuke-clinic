package daisukeclinic.routes;

import java.time.LocalDate;
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
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.MedicalRecord;
import daisukeclinic.model.Patient;
import daisukeclinic.model.Person;
import daisukeclinic.model.User;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;
import daisukeclinic.view.AppConsole;

public class AdminRoutes {

    // ================ PATIENT ===============

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
        removePatientMenuList.addMenuItem(new MenuItem("Back", () -> AppConsole.getInstance().menuStack.pop()));
        AppConsole.getInstance().menuStack.push(removePatientMenuList);
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
        searchPatientMenuList.addMenuItem(new MenuItem("Back", () -> AppConsole.getInstance().menuStack.pop()));
        AppConsole.getInstance().menuStack.push(searchPatientMenuList);
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
        displayAllPatientMenuList.addMenuItem(new MenuItem("Back", () -> AppConsole.getInstance().menuStack.pop()));

        AppConsole.getInstance().menuStack.push(displayAllPatientMenuList);
    }

    // ================== DOCTOR ===========================
    public static void registerNewDoctor() {
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
    }

    public static void loginDoctorRoute() {
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
    }

    public static void logoutDoctorRoute() {
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
                System.out.println("Successfully logged out!");
            } else {
                System.out.println("Doctor is not logged in!");
            }
        } else {
            System.out.println("Can't find a Doctor with ID: " + doctorId + "!");
        }

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void viewAllDoctorsInThisClinicRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("All Doctors In Daisuke Clinic");

        DoctorList.getInstance().displayAllDoctors();

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void viewLastLoggedInDoctorsRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Last Logged In Doctors");

        DoctorLoginList.getInstance().getAllLoggedInDoctors();

        ConsoleUtility.pressAnyKeyToContinue();
    }

    // ===================== APPOINTMENTS ========================

    public static void scheduleAppointmentRoute() {
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
        Doctor doctor;
        while (true) {
            doctorId = ConsoleUtility.getIntPromptInput("Doctor's Id: ");
            doctor = DoctorList.getInstance().findDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("Doctor with that ID does not exist!");
                continue;
            }
            break;
        }

        LocalDate appointmentDate = ConsoleUtility.getLocalDatePromptInputFuture("Date: ");

        AppointmentManager.getInstance().scheduleAppointment(
                patientId,
                doctorId,
                LocalDateTime.of(
                        appointmentDate.getYear(),
                        appointmentDate.getMonth(),
                        appointmentDate.getDayOfMonth(),
                        doctor.getScheduleStart().getHour(),
                        doctor.getScheduleStart().getMinute()));

        System.out.println("Successfuly scheduled the appointment!\n");

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void proccessAppointmentRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Proccess Appointment");

        int doctorId = ConsoleUtility.getIntPromptInput("Doctor ID: ");
        Doctor doctor = DoctorList.getInstance().findDoctorById(doctorId);

        if (doctor != null) {
            if (doctor.isLoggedIn()) {
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
    }

    public static void cancelAppointmentRoute() {
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
    }

    public static void viewUpcomingAppointmentsRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Upcoming Appointments");

        AppointmentManager.getInstance().viewUpcomingAppointments();

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void registerAndConnectAccountRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Register and Connect MyDaisuke Account");

        final User.Role[] role = new User.Role[1];
        int roleId = -1;
        Person person;

        MenuList roleMenuList = new MenuList("Select Account Type", 3);
        roleMenuList.addMenuItem(new MenuItem("Patient", () -> {
            role[0] = User.Role.ROLE_PATIENT;
        }));
        roleMenuList.addMenuItem(new MenuItem("Doctor", () -> {
            role[0] = User.Role.ROLE_DOCTOR;
        }));

        roleMenuList.printMenu();
        roleMenuList.run(roleMenuList.prompt());

        if (role[0] == null) {
            System.out.println("Please select either of two types, exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());

        if (role[0] == User.Role.ROLE_DOCTOR) {
            roleId = ConsoleUtility.getIntPromptInput("Enter Doctor ID to Connect: ");
            Doctor d = DoctorList.getInstance().findDoctorById(roleId);

            if (d == null) {
                System.out.println("Doctor with that ID did not exist! exiting...");
                ConsoleUtility.pressAnyKeyToContinue();
                return;
            }

            if (d.isConnectedToAccount()) {
                System.out.println("This doctor is already connected into an account! exiting...");
                ConsoleUtility.pressAnyKeyToContinue();
                return;
            }

            d.setIsConnectedAccount(true);
            person = d;

        } else if (role[0] == User.Role.ROLE_PATIENT) {
            roleId = ConsoleUtility.getIntPromptInput("Enter Patient ID to Connect: ");
            Patient p = PatientRecord.getInstance().findPatientById(roleId);

            if (p == null) {
                System.out.println("Patient with that ID did not exist! exiting...");
                ConsoleUtility.pressAnyKeyToContinue();
                return;
            }

            if (p.isConnectedToAccount()) {
                System.out.println("This patient is already connected into an account! exiting...");
                ConsoleUtility.pressAnyKeyToContinue();
                return;
            }

            p.setIsConnectedAccount(true);
            person = p;

        } else {
            System.out.println("Please select either Doctor or Patient! exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        ConsoleUtility.printTitle(
                "Connect MyDaisuke Account for " + person.getName());

        System.out.println("Note: username can be different from real name");
        String username = ConsoleUtility.getStringPromptInput("New username: ");

        if (UserListManager.getInstance().getUser(username) != null) {
            System.out.println("Username already exist! exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        String password = ConsoleUtility.getPasswordPromptInput("New Password: ");

        UserListManager.getInstance().addNewUser(username, password, role[0], roleId);

        System.out.println("User is successfuly connected! Now user can login using their MyDaisuke account!");

        ConsoleUtility.pressAnyKeyToContinue();
    }
}
