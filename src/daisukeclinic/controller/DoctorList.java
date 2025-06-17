package daisukeclinic.controller;

import java.io.Serializable;
import java.time.LocalTime;

import daisukeclinic.datastructure.LinkedList;
import daisukeclinic.model.Doctor;
import daisukeclinic.model.Person;
import daisukeclinic.utils.TableUtility;

public class DoctorList implements Serializable {
    private LinkedList<Doctor> doctorList;
    private static DoctorList instance;
    private int lastId = 0;

    private DoctorList() {
        doctorList = new LinkedList<>();
    }

    public static DoctorList getInstance() {
        if (instance == null) {
            instance = new DoctorList();
        }
        return instance;
    }

    public static void setInstance(DoctorList newInstance) {
        instance = newInstance;
    }

    public LinkedList<Doctor> getList() {
        return doctorList;
    }

    public int registerDoctor(String name, String specialty, LocalTime schedulStart, LocalTime scheduleEnd) {
        lastId += 1;
        doctorList.insertBack(new Doctor(lastId, name, specialty, schedulStart, scheduleEnd));
        return lastId;
    }

    public Doctor findDoctorById(int id) {
        Doctor decoy = new Doctor(id, null, null, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);
        return doctorList.find(decoy);
    }

    public void displayAllDoctors() {
        TableUtility.displayDoctorTable(doctorList);
    }
}
