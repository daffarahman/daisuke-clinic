package daisukeclinic.routes;

import daisukeclinic.view.AppConsole;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.Patient;
import daisukeclinic.model.User;
import daisukeclinic.utils.ConsoleUtility;

public class GlobalRoutes {
    public static void aboutMeRoute() {
        ConsoleUtility.clearScreen();

        User u = AppConsole.getInstance().currentUser;

        ConsoleUtility.printTitle("About Me");

        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
        System.out.println(
                "Username\t: " + u.getUsername() +
                        "\nRole\t\t: " + u.getRole());
        if (u.getRole() == User.Role.ROLE_PATIENT) {
            Patient p = PatientRecord.getInstance().findPatientById(u.getRoleId());
            if (p != null) {
                System.out.println("Full Name\t: " + p.getName());
                System.out.println("Address\t\t: " + p.getAddress());
                System.out.println("Phone\t\t: " + p.getPhoneNumber());
            }
        } else if (u.getRole() == User.Role.ROLE_DOCTOR) {
            Doctor d = DoctorList.getInstance().findDoctorById(u.getRoleId());
            if (d != null) {
                System.out.println("Full Name\t: " + d.getName());
                System.out.println("Specialty\t: " + d.getSpecialty());
                System.out.println("Schedule\t: " + d.getScheduleStart() + "-" + d.getScheduleEnd());
            }
        }

        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
        System.out.print("\n");
        ConsoleUtility.pressAnyKeyToContinue();
    }
}
