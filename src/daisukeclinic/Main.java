package daisukeclinic;

import javax.swing.SwingUtilities;

import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.view.AppConsole;
import daisukeclinic.view.AppFrame;
import daisukeclinic.view.components.console.MenuItem;
import daisukeclinic.view.components.console.MenuList;

public class Main {

    public Main() {
        loadData();

        MenuList bootMenuList = new MenuList("Daisuke Clinic Init", 3);
        bootMenuList.addMenuItem(new MenuItem("Console Mode", AppConsole::new));
        bootMenuList.addMenuItem(new MenuItem("GUI Mode", () -> SwingUtilities.invokeLater(AppFrame::new)));
        bootMenuList.addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

        while (true) {
            ConsoleUtility.clearScreen();
            bootMenuList.printMenu();
            bootMenuList.run(bootMenuList.prompt());
        }
    }

    public void loadData() {
        DoctorList.getInstance().registerDoctor("Dr. Amba", "Neurology");
        DoctorList.getInstance().registerDoctor("Dr. Rusdi", "Orthopedic");
        DoctorList.getInstance().registerDoctor("Dr. King", "Dentistry");

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