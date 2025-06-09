package daisukeclinic.controller;

import java.io.Serializable;

import daisukeclinic.model.Doctor;
import daisukeclinic.model.datastructure.LinkedList;
import daisukeclinic.model.datastructure.Node;

public class DoctorLoginList implements Serializable {
    private static DoctorLoginList instance;
    private LinkedList<Doctor> doctorLogins;

    private DoctorLoginList() {
        doctorLogins = new LinkedList<Doctor>();
    }

    public static DoctorLoginList getInstance() {
        if (instance == null) {
            instance = new DoctorLoginList();
        }
        return instance;
    }

    public boolean loginDoctor(Doctor doctor) {
        Node<Doctor> current = doctorLogins.getHead();

        if (current == null) {
            doctorLogins.insertBack(doctor);
        } else {
            while (current != null) {
                if (current.data.getId() == doctor.getId()) {
                    return false;
                }
                current = current.next;
            }
            doctorLogins.insertBack(doctor);
        }

        doctor.updateLoginTime();
        return true;
    }

    public void logoutDoctor(int doctorId) {
        Node<Doctor> current = doctorLogins.getHead();
        while (current != null) {
            if (current.data.getId() == doctorId) {
                doctorLogins.remove(current.data);
                return;
            }
            current = current.next;
        }
    }

    public void getAllLoggedInDoctors() {
        doctorLogins.display();
    }
}
