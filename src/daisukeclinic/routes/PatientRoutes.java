package daisukeclinic.routes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.AppointmentQueue;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.Patient;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;
import daisukeclinic.view.AppConsole;

public class PatientRoutes {
    public static void scheduleAppointmentRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Schedule Appointment");

        int patientId = AppConsole.getInstance().currentUser.getRoleId();

        int doctorId = 0;
        Doctor doctor;
        while (true) {
            doctorId = ConsoleUtility.getIntPromptInput("Doctor's Id: ");
            doctor = DoctorList.getInstance().findDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("Doctor with that ID does not exist!");
                continue;
            }
            break;
        }

        LocalDate appointmentDate = ConsoleUtility.getLocalDatePromptInputFuture("Date: ");

        AppointmentManager.getInstance().scheduleAppointment(
                patientId,
                doctorId,
                LocalDateTime.of(
                        appointmentDate.getYear(),
                        appointmentDate.getMonth(),
                        appointmentDate.getDayOfMonth(),
                        doctor.getScheduleStart().getHour(),
                        doctor.getScheduleStart().getMinute()));

        System.out.println("Successfuly scheduled the appointment!\n");

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void cancelAppointmentRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Cancel Appointment");

        int currentPatientId = AppConsole.getInstance().currentUser.getRoleId();
        int doctorId = ConsoleUtility.getIntPromptInput("Enter doctor ID: ");
        AppointmentQueue appointmentQueue = AppointmentManager.getInstance().getAppointmentQueue(doctorId);

        if (appointmentQueue != null) {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle("Select Appointment to Cancel");

            LinkedList<Integer> allowedId = new LinkedList<>();
            LinkedList<Appointment> myAppointments = new LinkedList<>();

            for (int i = 0; i < appointmentQueue.getQueue().getSize(); i++) {
                Appointment apt = appointmentQueue.getQueue().getIndex(i);
                if (apt.getPatientId() == currentPatientId) {
                    myAppointments.insertBack(apt);
                    allowedId.insertBack(apt.getId());
                }
            }

            if (myAppointments.getSize() == 0) {
                System.out.println("You have no appointments with this doctor.\n");
                ConsoleUtility.pressAnyKeyToContinue();
                return;
            }

            TableUtility.displayAppointmentTable(myAppointments);

            int appointmentId = -1;
            do {
                appointmentId = ConsoleUtility.getIntPromptInput("Enter Appointment ID: ");

                if (allowedId.find(appointmentId) == null) {
                    System.out.println("Enter 'your' Appointment ID!");
                }

            } while (allowedId.find(appointmentId) == null);

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
        ConsoleUtility.printTitle("My Upcoming Appointments");

        LinkedList<Appointment> a = new LinkedList<>();
        int currentPatientId = AppConsole.getInstance().currentUser.getRoleId();

        var appointments = AppointmentManager.getInstance().getAppointments();
        int doctorCount = appointments.getEntries().getSize();

        for (int i = 0; i < doctorCount; i++) {
            var doctorEntry = appointments.getEntries().getIndex(i);
            var queue = doctorEntry.value.getQueue();

            for (int j = 0; j < queue.getSize(); j++) {
                Appointment appointment = queue.getIndex(j);
                if (appointment != null && appointment.getPatientId() == currentPatientId) {
                    a.insertBack(appointment);
                }
            }
        }

        TableUtility.displayAppointmentTable(a);
        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void viewMedicalRecordsRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Your Medical Record");

        int patientId = AppConsole.getInstance().currentUser.getRoleId();
        Patient patient = PatientRecord.getInstance().findPatientById(patientId);
        if (patient != null) {
            ConsoleUtility.clearScreen();
            ConsoleUtility.printTitle(String.format("%s's Medical Record", patient.getName()));
            TableUtility.displayMedicalRecordsTable(patient.getMedicalRecords());
        } else {
            System.out.println("Patient with ID: " + patientId + " does not exist!\n");
        }

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void viewAllDoctorsScheduleRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Doctor Schedules");

        TableUtility.displayDoctorTable(DoctorList.getInstance().getList());

        ConsoleUtility.pressAnyKeyToContinue();
    }
}
