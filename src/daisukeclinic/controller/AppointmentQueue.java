package daisukeclinic.controller;

import java.time.LocalDateTime;

import daisukeclinic.model.Appointment;
import daisukeclinic.model.datastructure.Queue;

public class AppointmentQueue {
    private Queue<Appointment> appointments;
    private int lastId = 0;

    public AppointmentQueue() {
        appointments = new Queue<>();
    }

    public void scheduleAppointment(int patientId, int doctorId, LocalDateTime time) {
        lastId++;
        Appointment newAppointment = new Appointment(lastId, patientId, doctorId, time);

        // Empty queue or new appointment is earlier than the first appointment
        if (appointments.isEmpty() || time.isBefore(appointments.peek().getTime())) {
            Queue<Appointment> tempQueue = new Queue<>();
            tempQueue.enqueue(newAppointment);

            // Move all existing appointments to the temporary queue
            while (!appointments.isEmpty()) {
                tempQueue.enqueue(appointments.dequeue());
            }

            appointments = tempQueue;
        } else {
            // Create a temporary queue
            Queue<Appointment> tempQueue = new Queue<>();

            // Dequeue appointments with earlier times
            while (!appointments.isEmpty() &&
                    !appointments.peek().getTime().isAfter(time)) {
                tempQueue.enqueue(appointments.dequeue());
            }

            // Add the new appointment
            tempQueue.enqueue(newAppointment);

            // Add remaining appointments
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
