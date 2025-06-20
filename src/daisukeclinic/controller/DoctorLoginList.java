package daisukeclinic.controller;

import java.io.Serializable;

import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.datastructure.Node;
import daisukeclinic.model.Doctor;
import daisukeclinic.utils.TableUtility;

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

    public static void setInstance(DoctorLoginList newInstance) {
        instance = newInstance;
    }

    public boolean loginDoctor(Doctor doctor) {
        Node<Doctor> current = doctorLogins.getHead();

        if (current == null) {
            doctorLogins.insertBack(doctor);
        } else {
            while (current != null) {
                if (current.data.compareTo(doctor) == 0) {
                    return false;
                }
                current = current.next;
            }
            doctorLogins.insertBack(doctor);
        }
        doctor.login();
        doctor.updateLoginTime();
        return true;
    }

    public boolean logoutDoctor(int doctorId) {
        Node<Doctor> current = doctorLogins.getHead();
        while (current != null) {
            if (current.data.getId() == doctorId) {
                Doctor mainDoctor = DoctorList.getInstance().findDoctorById(doctorId);

                current.data.logout();
                current.data.updateLogoutTime();

                if (mainDoctor != null) {
                    mainDoctor.logout();
                    mainDoctor.updateLogoutTime();
                }

                doctorLogins.remove(current.data);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void getAllLoggedInDoctors() {
        TableUtility.displayDoctorLoginTable(doctorLogins);
    }

    public LinkedList<Doctor> getList() {
        return doctorLogins;
    }
}
