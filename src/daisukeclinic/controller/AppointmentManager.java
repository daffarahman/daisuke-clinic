package daisukeclinic.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import daisukeclinic.model.Appointment;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.model.datastructure.Map;
import daisukeclinic.model.datastructure.MapEntry;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.TableUtility;

public class AppointmentManager implements Serializable {
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

    public static void setInstance(AppointmentManager newInstance) {
        instance = newInstance;
    }

    public Map<Integer, AppointmentQueue> getAppointments() {
        return appointments;
    }

    public AppointmentQueue getAppointmentQueue(int doctorId) {
        if (appointments.isPresent(doctorId)) {
            return appointments.get(doctorId);
        }
        return null;
    }

    public void scheduleAppointment(int patientId, int doctorId, LocalDateTime time) {
        if (appointments.isPresent(doctorId)) {
            appointments.get(doctorId).scheduleAppointment(patientId, doctorId, time);
        } else {
            appointments.put(doctorId, new AppointmentQueue());
            appointments.get(doctorId).scheduleAppointment(patientId, doctorId, time);
        }
    }

    public Appointment proccessNextAppointment(int doctorId) {
        if (appointments.isPresent(doctorId)) {
            return appointments.get(doctorId).proccessNextAppointment();
        }
        return null;
    }

    public void viewUpcomingAppointments() {
        LinkedList<MapEntry<Integer, AppointmentQueue>> appointmentEntries = appointments.getEntries();
        for (int i = 0; i < appointmentEntries.getSize(); i++) {
            MapEntry<Integer, AppointmentQueue> entry = appointmentEntries.getIndex(i);
            if (!entry.value.getQueue().isEmpty()) {
                // System.out.println("Doctor ID: " + entry.key);
                System.out.print("\n");
                Doctor d = DoctorList.getInstance().findDoctorById(i);
                if (d != null) {
                    ConsoleUtility.printTitle(String.format("%d | %s | %s", entry.key, d.getName(), d.getSpecialty()));
                    TableUtility.displayAppointmentTable(appointmentEntries.getIndex(i).value.getQueue());
                }
            }
        }
    }
}
