package daisukeclinic.controller;

import daisukeclinic.model.datastructure.Map;

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

}
