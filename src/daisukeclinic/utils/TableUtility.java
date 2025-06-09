package daisukeclinic.utils;

import java.time.format.DateTimeFormatter;

import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
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
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    public static void displayDoctorTable(LinkedList<Doctor> list) {
        System.out.printf("%-5s %-20s %-20s %-20s %-20s%n", "ID", "Name", "Specialty",
                "Last Login", "Last Logout");
        System.out.println("-------------------------------------------------------------------------------------");
        if (list != null) {
            for (int i = 0; i < list.getSize(); i++) {
                Doctor doctor = list.getIndex(i);
                String loginTime = doctor.getLoginTime() == null ? "-"
                        : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(doctor.getLoginTime());
                String logoutTime = doctor.getLogoutTime() == null ? "-"
                        : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(doctor.getLogoutTime());

                System.out.printf("%-5d %-20s %-20s %-20s %-20s%n",
                        doctor.getId(), doctor.getName(), doctor.getSpecialty(),
                        loginTime, logoutTime);
            }
            System.out.println("-------------------------------------------------------------------------------------");
        }

    }

    public static void displayAppointmentTable(LinkedList<Appointment> list) {
        System.out.printf("%-5s %-15s %-15s %-25s %-25s %-15s%n", "ID", "Patient ID", "Doctor ID", "Patient Name",
                "Doctor Name", "Time");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        if (list != null) {
            for (int i = 0; i < list.getSize(); i++) {
                Appointment appointment = list.getIndex(i);
                Doctor currentDoctor = DoctorList.getInstance().findDoctorById(appointment.getDoctorId());
                Patient currentPatient = PatientRecord.getInstance().findPatientById(appointment.getPatientId());
                System.out.printf("%-5d %-15d %-15d %-25s %-25s %-15s%n",
                        appointment.getId(),
                        appointment.getPatientId(),
                        appointment.getDoctorId(),
                        (currentPatient == null) ? "ERR" : currentPatient.getName(),
                        (currentDoctor == null) ? "ERR" : currentDoctor.getName(),
                        appointment.getTime());
            }
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------");
        }

    }

}
