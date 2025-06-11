package daisukeclinic;

import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.SaveUtility;
import daisukeclinic.view.AppConsole;

public class Main {

    public Main() {
        ConsoleUtility.clearScreen();
        SaveUtility.loadAll();
        splashScreen();
        new AppConsole();
    }

    public void splashScreen() {
        System.out.println(" ____        _           _         ____ _ _       _      ____            \n" + //
                "|  _ \\  __ _(_)___ _   _| | _____ / ___| (_)_ __ (_) ___|  _ \\ _ __ ___  \n" + //
                "| | | |/ _` | / __| | | | |/ / _ \\ |   | | | '_ \\| |/ __| |_) | '__/ _ \\ \n" + //
                "| |_| | (_| | \\__ \\ |_| |   <  __/ |___| | | | | | | (__|  __/| | | (_) |\n" + //
                "|____/ \\__,_|_|___/\\__,_|_|\\_\\___|\\____|_|_|_| |_|_|\\___|_|   |_|  \\___/ \n\n" + //
                "                                                                         ");
        ConsoleUtility.pressAnyKeyToContinue();
    }

    // public void loadData() {
    // // DoctorList.getInstance().registerDoctor("Dr. Amba", "Neurology");
    // // DoctorList.getInstance().registerDoctor("Dr. Rusdi", "Orthopedic");
    // // DoctorList.getInstance().registerDoctor("Dr. King", "Dentistry");

    // // PatientRecord.getInstance().addPatient("Alice", 27, "123 Main St",
    // // "555-1234");
    // // PatientRecord.getInstance().addPatient("Bob", 34, "456 Elm St",
    // "555-5678");
    // // PatientRecord.getInstance().addPatient("Charlie", 29, "789 Oak St",
    // // "555-9012");
    // // PatientRecord.getInstance().addPatient("Diana", 22, "321 Pine St",
    // // "555-3456");
    // // PatientRecord.getInstance().addPatient("Ethan", 40, "654 Maple St",
    // // "555-7890");
    // // PatientRecord.getInstance().addPatient("Fiona", 31, "987 Cedar St",
    // // "555-2345");

    // // AppointmentManager.getInstance().scheduleAppointment(1, 0,
    // // LocalDateTime.now());
    // // AppointmentManager.getInstance().scheduleAppointment(2, 0,
    // // LocalDateTime.now());
    // // AppointmentManager.getInstance().scheduleAppointment(4, 0,
    // // LocalDateTime.now());
    // SaveUtility.loadAll();
    // }

    public static void main(String[] args) {
        new Main();
    }
}