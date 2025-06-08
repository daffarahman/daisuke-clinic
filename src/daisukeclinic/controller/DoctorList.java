package daisukeclinic.controller;

import daisukeclinic.model.Doctor;
import daisukeclinic.model.Person;
import daisukeclinic.model.datastructure.LinkedList;

public class DoctorList {
    private LinkedList<Doctor> doctorList;
    private static DoctorList instance;

    private DoctorList() {
        doctorList = new LinkedList<>();
    }

    public static DoctorList getInstance() {
        if (instance == null) {
            instance = new DoctorList();
        }
        return instance;
    }

    public LinkedList<Doctor> getList() {
        return doctorList;
    }

    public Doctor findDoctorById(int id) {
        Doctor decoy = new Doctor(id, null, null);
        decoy.setCompareMode(Person.CompareMode.COMPARE_BY_ID);
        return doctorList.find(decoy);
    }
}
