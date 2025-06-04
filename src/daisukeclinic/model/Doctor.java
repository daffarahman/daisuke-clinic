package daisukeclinic.model;

import java.time.LocalDateTime;

public class Doctor extends Person implements Comparable<Doctor> {
    private String specialty;
    private LocalDateTime loginTime;

    public Doctor(int id, String name, String specialty) {
        super(id, name);
        this.specialty = specialty.strip();
        this.loginTime = LocalDateTime.now();
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public void updateLoginTime() {
        this.loginTime = LocalDateTime.now();
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
        return compareToLocal(other);
    }
}
