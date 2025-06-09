package daisukeclinic.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MedicalRecord implements Comparable<MedicalRecord>, Serializable {
    private int doctorId;
    private LocalDateTime appointmentdate;
    private String problem;

    public MedicalRecord(int doctorId, LocalDateTime appointmentdate, String problem) {
        this.doctorId = doctorId;
        this.appointmentdate = appointmentdate;
        this.problem = problem;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getAppointmentdate() {
        return appointmentdate;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public int compareTo(MedicalRecord other) {
        if (getAppointmentdate() == other.getAppointmentdate()
                && getDoctorId() == other.getDoctorId() && getProblem() == other.getProblem()) {
            return 0;
        }
        return -1;
    }
}
