package daisukeclinic.utils;

import daisukeclinic.model.Appointment;
import daisukeclinic.model.Patient;
import daisukeclinic.model.datastructure.LinkedList;

public class TableUtility {
    public static void displayPatientTable(LinkedList<Patient> list) {
        System.out.printf("%-5s %-20s %-3s %-30s %-15s%n", "ID", "Name", "Age",
                "Address", "Phone Number");
        System.out.println("-------------------------------------------------------------------------------------");
        if (list != null) {
            for (int i = 0; i < list.getSize(); i++) {
                Patient patient = list.getIndex(i);
                System.out.printf("%-5d %-20s %-3d %-30s %-15s%n",
                        patient.getId(), patient.getName(), patient.getAge(),
                        patient.getAddress(), patient.getPhoneNumber());
            }
        }
    }

    public static void displayAppointmentTable(LinkedList<Appointment> list) {
        System.out.printf("%-5s %-20s %-20s %-15s%n", "ID", "Patient ID", "Doctor ID", "Time");
        System.out.println("------------------------------------------------------------------------");
        if (list != null) {
            for (int i = 0; i < list.getSize(); i++) {
                Appointment appointment = list.getIndex(i);
                System.out.printf("%-5d %-20d %-20d %-15s%n",
                        appointment.getId(), appointment.getPatientId(),
                        appointment.getDoctorId(),
                        appointment.getTime());
            }
        }
    }
}
