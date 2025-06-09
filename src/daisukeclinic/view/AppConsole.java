package daisukeclinic.view;

import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;

import java.io.Console;
import java.time.LocalDateTime;

import javax.swing.SwingUtilities;

import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.AppointmentQueue;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.controller.SearchablePatientTree;
import daisukeclinic.model.Patient;
import daisukeclinic.model.datastructure.LinkedList;
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
                ConsoleUtility.clearScreen();
                menuStack.peek().printMenu();
                menuStack.peek().run(menuStack.peek().prompt());
            }
        }
    }

    public void setupMenus() {
        mainMenuList = new MenuList("Daisuke Clinic", 5);
        managePatientMenuList = new MenuList("Manage Patient", 5);
        manageDoctorMenuList = new MenuList("Manage Doctor", 7);
        manageAppointmentMenuList = new MenuList("Manage Appointment", 5);

        mainMenuList.addMenuItem(new MenuItem("Manage Patients", () -> menuStack.push(managePatientMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Doctors", () -> menuStack.push(manageDoctorMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Manage Appointments", () -> menuStack.push(manageAppointmentMenuList)));
        mainMenuList.addMenuItem(new MenuItem("Instance GUI", () -> SwingUtilities.invokeLater(AppFrame::new)));
        mainMenuList.addMenuItem(new MenuItem("Exit", () -> menuStack.pop()));

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
            System.out.println("New patient successfuly added!\n");

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
                LinkedList<Patient> l = new LinkedList<>();
                l.insertBack(foundPatient);
                TableUtility.displayPatientTable(l);
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
        manageDoctorMenuList.addMenuItem(new MenuItem("Register New Doctor", null));

        // Login doctor
        manageDoctorMenuList.addMenuItem(new MenuItem("Login Doctor", null));

        // Logout doctor
        manageDoctorMenuList.addMenuItem(new MenuItem("Logout Doctor", null));

        // All doctors
        manageDoctorMenuList.addMenuItem(new MenuItem("View All Doctors In This Clinic", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("All Doctors In Daisuke Clinic");

            DoctorList.getInstance().displayAllDoctors();

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // All logged in doctors
        manageDoctorMenuList.addMenuItem(new MenuItem("View All Logged In Doctors", null));

        // Last logged in doctors
        manageDoctorMenuList.addMenuItem(new MenuItem("View Last Logged In Doctors", null));

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

            AppointmentManager.getInstance().scheduleAppointment(patientId, doctorId, appointmentTime);

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Process Appointment
        manageAppointmentMenuList.addMenuItem(new MenuItem("Proccess Appointment", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Proccess Appointment");

            int doctorId = ConsoleUtility.getIntPromptInput("Doctor ID: ");
            System.out.println(AppointmentManager.getInstance().getAppointments().isPresent(doctorId));

            AppointmentQueue appointments = AppointmentManager.getInstance().getDoctorQueue(doctorId);

            if (appointments != null) {
                if (appointments.getQueue().isEmpty()) {
                    System.out.println("No upcoming appointments for the Doctor with ID: " + doctorId +
                            "!");
                } else {
                    System.out.println("Doctor Found Appointment Proccessed!!");
                    System.out.println(
                            appointments.proccessNextAppointment());
                }
            } else {
                System.out.println("No upcoming appointments for the Doctor with ID: " + doctorId +
                        "!");
            }

            ConsoleUtility.pressAnyKeyToContinue();
        }));

        // Cancel Appointment
        manageAppointmentMenuList.addMenuItem(new MenuItem("Cancel Appointment", null));

        // View Upcoming Appointments
        manageAppointmentMenuList.addMenuItem(new MenuItem("View Upcoming Appointments", () -> {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Upcoming Appointments");

            AppointmentManager.getInstance().viewUpcomingAppointments();

            ConsoleUtility.pressAnyKeyToContinue();

        }));
        manageAppointmentMenuList.addMenuItem(new MenuItem("Back", () -> menuStack.pop()));

        // ========== END MANAGE APPOINTMENT ==========
    }
}
