package daisukeclinic.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MedicalRecord implements Comparable<MedicalRecord>, Serializable {
    private int doctorId;
    private LocalDateTime appointmentdate;
    private String problem;
    private String diagnosis;
    private String drug;

    public MedicalRecord(int doctorId, LocalDateTime appointmentdate, String problem, String diagnosis, String drug) {
        this.doctorId = doctorId;
        this.appointmentdate = appointmentdate;
        this.problem = problem;
        this.diagnosis = diagnosis;
        this.drug = drug;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
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
