package daisukeclinic;

import javax.swing.SwingUtilities;

import daisukeclinic.controller.PatientRecord;
import daisukeclinic.utils.Utility;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.Patient;
import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.view.AppConsole;
import daisukeclinic.view.AppFrame;
import daisukeclinic.view.components.console.MenuItem;
import daisukeclinic.view.components.console.MenuList;

public class Main {

    public static LinkedList<Doctor> doctors;
    public static LinkedList<Patient> patients;

    public Main() {
        loadData();

        MenuList bootMenuList = new MenuList("Daisuke Clinic Init", 3);
        bootMenuList.addMenuItem(new MenuItem("Console Mode", AppConsole::new));
        bootMenuList.addMenuItem(new MenuItem("GUI Mode", () -> SwingUtilities.invokeLater(AppFrame::new)));
        bootMenuList.addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

        while (true) {
            Utility.clearScreen();
            bootMenuList.printMenu();
            bootMenuList.run(bootMenuList.prompt());
        }
    }

    public void loadData() {
        Main.doctors = new LinkedList<>();
        Main.doctors.insertBack(new Doctor(1, "Dr. Smith", "Cardiology"));
        Main.doctors.insertBack(new Doctor(2, "Dr. Jones", "Neurology"));
        Main.doctors.insertBack(new Doctor(3, "Dr. Brown", "Pediatrics"));
        Main.doctors.insertBack(new Doctor(4, "Dr. Taylor", "Orthopedics"));
        Main.doctors.insertBack(new Doctor(5, "Dr. Wilson", "Dermatology"));
        Main.doctors.insertBack(new Doctor(6, "Dr. Johnson", "Oncology"));

        PatientRecord.getInstance().addPatient("Alice", 27, "123 Main St", "555-1234");
        PatientRecord.getInstance().addPatient("Bob", 34, "456 Elm St", "555-5678");
        PatientRecord.getInstance().addPatient("Charlie", 29, "789 Oak St", "555-9012");
        PatientRecord.getInstance().addPatient("Diana", 22, "321 Pine St", "555-3456");
        PatientRecord.getInstance().addPatient("Ethan", 40, "654 Maple St", "555-7890");
        PatientRecord.getInstance().addPatient("Fiona", 31, "987 Cedar St", "555-2345");
    }

    public static void main(String[] args) {
        new Main();
    }
}