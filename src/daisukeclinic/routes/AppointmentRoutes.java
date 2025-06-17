package daisukeclinic.routes;

import java.time.LocalDateTime;

import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.AppointmentQueue;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.MedicalRecord;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;

public class AppointmentRoutes {
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
        while (true) {
            doctorId = ConsoleUtility.getIntPromptInput("Doctor's Id: ");
            if (DoctorList.getInstance().findDoctorById(doctorId) == null) {
                System.out.println("Doctor with that ID does not exist!");
                continue;
            }
            break;
        }

        LocalDateTime appointmentTime = ConsoleUtility.getDateTimePromptInput("Date & Time: ");

        // System.out.println("Appointment Time");

        AppointmentManager.getInstance().scheduleAppointment(patientId, doctorId, appointmentTime);

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

                System.out.println(AppointmentManager.getInstance().getAppointments().isPresent(doctorId));

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
}
