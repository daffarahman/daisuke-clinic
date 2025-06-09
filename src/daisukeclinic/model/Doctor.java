package daisukeclinic.model;

import java.time.LocalDateTime;

public class Doctor extends Person implements Comparable<Doctor> {
    private String specialty;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    public Doctor(int id, String name, String specialty) {
        super(id, name);
        this.specialty = (specialty == null) ? "" : specialty.strip();
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void updateLoginTime() {
        loginTime = LocalDateTime.now();
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void updateLogoutTime() {
        logoutTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "Id=" + getId() +
                ", name='" + getName() + '\'' +
                ", specialty='" + specialty + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }

    @Override
    public int compareTo(Doctor other) {
        return compareWithAnotherPerson(other);
    }
}
