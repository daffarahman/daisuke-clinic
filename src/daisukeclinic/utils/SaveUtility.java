package daisukeclinic.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import daisukeclinic.controller.AppointmentManager;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.DoctorLoginList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.controller.SearchablePatientTree;
import daisukeclinic.controller.UserListManager;
import daisukeclinic.utils.adapter.LocalDateTimeAdapter;
import daisukeclinic.utils.adapter.LocalTimeAdapter;

public class SaveUtility {
    private static final String DATA_DIR = "db";
    private static final String PATIENT_FILE = DATA_DIR + "/patients.json";
    private static final String DOCTOR_FILE = DATA_DIR + "/doctors.json";
    private static final String APPOINTMENT_FILE = DATA_DIR + "/appointments.json";
    private static final String LOGIN_FILE = DATA_DIR + "/doctor_logins.json";
    private static final String TREE_FILE = DATA_DIR + "/patient_tree.json";
    private static final String USER_FILE = DATA_DIR + "/user.json";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .setPrettyPrinting()
            .create();

    public static void saveAll() {
        new File(DATA_DIR).mkdirs();
        try {
            writeToFile(PATIENT_FILE, PatientRecord.getInstance());
            writeToFile(DOCTOR_FILE, DoctorList.getInstance());
            writeToFile(APPOINTMENT_FILE, AppointmentManager.getInstance());
            writeToFile(LOGIN_FILE, DoctorLoginList.getInstance());
            writeToFile(TREE_FILE, SearchablePatientTree.getInstance());
            writeToFile(USER_FILE, UserListManager.getInstance());
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            ConsoleUtility.pressAnyKeyToContinue();
        }
    }

    public static void loadAll() {
        try {
            if (!new File(DATA_DIR).exists())
                return;

            PatientRecord.setInstance(readFromFile(PATIENT_FILE, PatientRecord.class));
            DoctorList.setInstance(readFromFile(DOCTOR_FILE, DoctorList.class));
            AppointmentManager.setInstance(readFromFile(APPOINTMENT_FILE, AppointmentManager.class));
            DoctorLoginList.setInstance(readFromFile(LOGIN_FILE, DoctorLoginList.class));
            SearchablePatientTree.setInstance(readFromFile(TREE_FILE, SearchablePatientTree.class));
            UserListManager.setInstance(readFromFile(USER_FILE, UserListManager.class));
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            ConsoleUtility.pressAnyKeyToContinue();
        }
    }

    private static void writeToFile(String file, Object obj) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(obj, writer);
        }
    }

    private static <T> T readFromFile(String file, Class<T> type) throws IOException {
        if (!new File(file).exists())
            return null;
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        }
    }
}