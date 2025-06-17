package daisukeclinic.routes;

import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.MedicalRecord;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;
import daisukeclinic.view.AppConsole;

public class DoctorRoutes {
    public static void proccessAppointmentRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Proccess Appointment");

        int doctorId = AppConsole.getInstance().currentUser.getRoleId();
        Doctor doctor = DoctorList.getInstance().findDoctorById(doctorId);

        if (doctor != null) {
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

                System.out.println("\nAppointment proccessed, hope the patient will get well soon!\n");
            } else {
                System.out.println("\nYou don't have any appointments!.\n");
            }
        }

        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void viewUpcomingAppointmentsRoute() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("My Upcoming Appointments");

        LinkedList<Appointment> a = new LinkedList<>();
        int currentDoctorId = AppConsole.getInstance().currentUser.getRoleId();
        var appointmentQueue = AppointmentManager.getInstance().getAppointmentQueue(currentDoctorId);

        if (appointmentQueue != null) {
            var queue = appointmentQueue.getQueue();
            for (int i = 0; i < queue.getSize(); i++) {
                a.insertBack(queue.getIndex(i));
            }
        }

        TableUtility.displayAppointmentTable(a);
        ConsoleUtility.pressAnyKeyToContinue();
    }
}
