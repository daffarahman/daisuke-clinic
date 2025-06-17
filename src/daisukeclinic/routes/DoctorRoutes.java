package daisukeclinic.routes;

import java.time.LocalTime;

import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.DoctorLoginList;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Doctor;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;

public class DoctorRoutes {
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
                System.out.println("Successfully logged out in!");
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
}
