package daisukeclinic.controller;

import java.time.LocalDateTime;

import daisukeclinic.model.Appointment;
import daisukeclinic.model.datastructure.Queue;

public class AppointmentQueue {
    private static AppointmentQueue instance;
    private Queue<Appointment> appointments;
    private int lastId = 0;

    private AppointmentQueue() {
        appointments = new Queue<>();
    }

    public static AppointmentQueue getInstance() {
        if (instance == null) {
            instance = new AppointmentQueue();
        }
        return instance;
    }

    public void scheduleAppointment(int patientId, int doctorId, LocalDateTime time) {
        appointments.enqueue(new Appointment(lastId++, patientId, doctorId, time));
    }

    public Appointment proccessNextAppointment() {
        return appointments.dequeue();
    }

    public void viewUpcomingAppointments() {
        appointments.display();
    }
}
