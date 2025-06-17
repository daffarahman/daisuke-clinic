package daisukeclinic.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import daisukeclinic.datastructure.Queue;
import daisukeclinic.model.Appointment;

public class AppointmentQueue implements Serializable {
    private Queue<Appointment> appointments;
    private int lastId = 0;

    public AppointmentQueue() {
        appointments = new Queue<>();
    }

    public void scheduleAppointment(int patientId, int doctorId, LocalDateTime time) {
        lastId++;
        Appointment newAppointment = new Appointment(lastId, patientId, doctorId, time);

        if (appointments.isEmpty() || time.isBefore(appointments.peek().getTime())) {
            Queue<Appointment> tempQueue = new Queue<>();
            tempQueue.enqueue(newAppointment);

            while (!appointments.isEmpty()) {
                tempQueue.enqueue(appointments.dequeue());
            }

            appointments = tempQueue;
        } else {
            Queue<Appointment> tempQueue = new Queue<>();

            while (!appointments.isEmpty() &&
                    !appointments.peek().getTime().isAfter(time)) {
                tempQueue.enqueue(appointments.dequeue());
            }

            tempQueue.enqueue(newAppointment);

            while (!appointments.isEmpty()) {
                tempQueue.enqueue(appointments.dequeue());
            }

            appointments = tempQueue;
        }
    }

    public Appointment proccessNextAppointment() {
        return appointments.dequeue();
    }

    public void viewUpcomingAppointments() {
        appointments.display();
    }

    public Queue<Appointment> getQueue() {
        return appointments;
    }
}
