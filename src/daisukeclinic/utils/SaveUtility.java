package daisukeclinic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.DoctorLoginList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.controller.SearchablePatientTree;

public class SaveUtility {
    private static final String DATA_FILE = "clinic_data.dat";

    public static void saveAll() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            DataContainer container = new DataContainer(
                    PatientRecord.getInstance(),
                    DoctorList.getInstance(),
                    AppointmentManager.getInstance(),
                    DoctorLoginList.getInstance(),
                    SearchablePatientTree.getInstance());
            out.writeObject(container);
            System.out.println("All data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public static void loadAll() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                return;
            }

            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(file))) {
                DataContainer container = (DataContainer) in.readObject();

                // Restore all instances
                PatientRecord.setInstance(container.patientRecord);
                DoctorList.setInstance(container.doctorList);
                AppointmentManager.setInstance(container.appointmentManager);
                DoctorLoginList.setInstance(container.doctorLoginList);
                SearchablePatientTree.setInstance(container.searchablePatientTree);

                System.out.println("All data loaded successfully!");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private static class DataContainer implements Serializable {
        private static final long serialVersionUID = 1L;

        final PatientRecord patientRecord;
        final DoctorList doctorList;
        final AppointmentManager appointmentManager;
        final DoctorLoginList doctorLoginList;
        final SearchablePatientTree searchablePatientTree;

        DataContainer(
                PatientRecord patientRecord,
                DoctorList doctorList,
                AppointmentManager appointmentManager,
                DoctorLoginList doctorLoginList,
                SearchablePatientTree searchablePatientTree) {
            this.patientRecord = patientRecord;
            this.doctorList = doctorList;
            this.appointmentManager = appointmentManager;
            this.doctorLoginList = doctorLoginList;
            this.searchablePatientTree = searchablePatientTree;
        }
    }
}