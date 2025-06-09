package daisukeclinic.controller;

import java.time.LocalDateTime;

import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.model.datastructure.Map;
import daisukeclinic.model.datastructure.MapEntry;
import daisukeclinic.utils.TableUtility;

public class AppointmentManager {
    private static AppointmentManager instance;
    private Map<Integer, AppointmentQueue> appointments;

    private AppointmentManager() {
        appointments = new Map<>();
    }

    public static AppointmentManager getInstance() {
        if (instance == null) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    public Map<Integer, AppointmentQueue> getAppointments() {
        return appointments;
    }

    public AppointmentQueue getDoctorQueue(int doctorId) {
        return appointments.get(doctorId);
    }

    public void scheduleAppointment(int patientId, int doctorId, LocalDateTime time) {
        if (appointments.isPresent(doctorId)) {
            appointments.get(doctorId).scheduleAppointment(patientId, doctorId, time);
        } else {
            appointments.put(doctorId, new AppointmentQueue());
            appointments.get(doctorId).scheduleAppointment(patientId, doctorId, time);
        }
    }

    public void viewUpcomingAppointments() {
        LinkedList<MapEntry<Integer, AppointmentQueue>> appointmentEntries = appointments.getEntries();
        for (int i = 0; i < appointmentEntries.getSize(); i++) {
            MapEntry<Integer, AppointmentQueue> entry = appointmentEntries.getIndex(i);
            if (!entry.value.getQueue().isEmpty()) {
                System.out.println("Doctor id: " + entry.key);
                TableUtility.displayAppointmentTable(appointmentEntries.getIndex(i).value.getQueue());
            }
        }
    }
}
