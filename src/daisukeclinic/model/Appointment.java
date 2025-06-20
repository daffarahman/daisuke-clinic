package daisukeclinic.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Appointment implements Comparable<Appointment>, Serializable {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime time;

    public Appointment(int id, int patientId, int doctorId, LocalDateTime time) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int compareTo(Appointment other) {
        return Integer.compare(id, other.getId());
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", time=" + time +
                '}';
    }
}
