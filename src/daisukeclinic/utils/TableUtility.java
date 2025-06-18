package daisukeclinic.utils;

import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.MedicalRecord;
import daisukeclinic.model.Patient;

public class TableUtility {
        public static void displayPatientTable(LinkedList<Patient> list) {
                System.out.printf("%-5s %-20s %-3s %-30s %-15s %-6s%n", "ID", "Name", "Age",
                                "Address", "Phone Number", "Account Connected");
                ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                if (list != null) {
                        for (int i = 0; i < list.getSize(); i++) {
                                Patient patient = list.getIndex(i);
                                System.out.printf("%-5d %-20s %-3d %-30s %-15s %-6s%n",
                                                patient.getId(), patient.getName(), patient.getAge(),
                                                patient.getAddress(), patient.getPhoneNumber(),
                                                patient.isConnectedToAccount());
                        }
                        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                }
        }

        public static void displayMedicalRecordsTable(LinkedList<MedicalRecord> list) {
                System.out.printf("%-30s %-25s %-25s %-25s %-25s%n", "Date & Time", "Doctor",
                                "Problem", "Diagnosis", "Drug");
                ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                if (list != null) {
                        for (int i = 0; i < list.getSize(); i++) {
                                MedicalRecord record = list.getIndex(i);
                                System.out.printf("%-30s %-25s %-25s %-25s %-25s%n",
                                                Utility.formatLocalDateTime(record.getAppointmentdate()),
                                                DoctorList.getInstance().findDoctorById(record.getDoctorId()).getName(),
                                                record.getProblem(), record.getDiagnosis(), record.getDrug());
                        }
                        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                }
        }

        public static void displayDoctorCompleteTable(LinkedList<Doctor> list) {
                System.out.printf("%-5s %-20s %-15s %-12s %-25s %-25s %-6s%n", "ID", "Name", "Specialty", "Schedule",
                                "Last Login", "Last Logout", "Account Connected");
                ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                if (list != null) {
                        for (int i = 0; i < list.getSize(); i++) {
                                Doctor doctor = list.getIndex(i);
                                String loginTime = doctor.getLoginTime() == null ? "-"
                                                : Utility.formatLocalDateTime(doctor.getLoginTime());
                                String logoutTime = doctor.getLogoutTime() == null ? "-"
                                                : Utility.formatLocalDateTime(doctor.getLogoutTime());

                                System.out.printf("%-5d %-20s %-15s %-12s %-25s %-25s %-6s%n",
                                                doctor.getId(), doctor.getName(), doctor.getSpecialty(),
                                                String.format("%s-%s", doctor.getScheduleStart(),
                                                                doctor.getScheduleEnd()),
                                                loginTime, logoutTime, doctor.isConnectedToAccount() ? "Yes" : "No");
                        }
                        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                }

        }

        public static void displayDoctorTable(LinkedList<Doctor> list) {
                System.out.printf("%-5s %-20s %-15s %-12s%n", "ID", "Name", "Specialty", "Schedule");
                ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                if (list != null) {
                        for (int i = 0; i < list.getSize(); i++) {
                                Doctor doctor = list.getIndex(i);
                                System.out.printf("%-5d %-20s %-15s %-12s%n",
                                                doctor.getId(), doctor.getName(), doctor.getSpecialty(),
                                                String.format("%s-%s", doctor.getScheduleStart(),
                                                                doctor.getScheduleEnd()));
                        }
                        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                }

        }

        public static void displayDoctorLoginTable(LinkedList<Doctor> list) {
                System.out.printf("%-5s %-20s %-20s %-20s%n", "ID", "Name", "Specialty",
                                "Login Time");
                ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                if (list != null) {
                        for (int i = 0; i < list.getSize(); i++) {
                                Doctor doctor = list.getIndex(i);
                                String loginTime = doctor.getLoginTime() == null ? "-"
                                                : Utility.formatLocalDateTime(doctor.getLoginTime());

                                System.out.printf("%-5d %-20s %-20s %-20s%n",
                                                doctor.getId(), doctor.getName(), doctor.getSpecialty(),
                                                loginTime);
                        }
                        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                }
        }

        public static void displayAppointmentTable(LinkedList<Appointment> list) {
                System.out.printf("%-5s %-15s %-15s %-25s %-25s %-15s%n", "ID", "Patient ID", "Doctor ID",
                                "Patient Name",
                                "Doctor Name", "Time");
                ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                if (list != null) {
                        for (int i = 0; i < list.getSize(); i++) {
                                Appointment appointment = list.getIndex(i);
                                Doctor currentDoctor = DoctorList.getInstance()
                                                .findDoctorById(appointment.getDoctorId());
                                Patient currentPatient = PatientRecord.getInstance()
                                                .findPatientById(appointment.getPatientId());
                                System.out.printf("%-5d %-15d %-15d %-25s %-25s %-15s%n",
                                                appointment.getId(),
                                                appointment.getPatientId(),
                                                appointment.getDoctorId(),
                                                (currentPatient == null) ? "ERR" : currentPatient.getName(),
                                                (currentDoctor == null) ? "ERR" : currentDoctor.getName(),
                                                Utility.formatLocalDateTime(appointment.getTime()));
                        }
                        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());
                }

        }

}
