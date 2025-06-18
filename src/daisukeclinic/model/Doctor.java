package daisukeclinic.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Doctor extends Person implements Comparable<Doctor> {
    private String specialty;

    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    private boolean isLoggedIn;

    private LocalTime scheduleStart;
    private LocalTime scheduleEnd;

    public Doctor(int id, String name, String specialty, LocalTime scheduleStart, LocalTime scheduleEnd) {
        super(id, name);
        this.specialty = (specialty == null || specialty.isEmpty() || specialty.isBlank()) ? "General"
                : specialty.strip();
        isLoggedIn = false;
        this.scheduleStart = (scheduleStart == null) ? LocalTime.of(8, 0) : scheduleStart;
        this.scheduleEnd = (scheduleEnd == null) ? LocalTime.of(8, 0) : scheduleEnd;
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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public LocalTime getScheduleStart() {
        return scheduleStart;
    }

    public LocalTime getScheduleEnd() {
        return scheduleEnd;
    }

    public void login() {
        isLoggedIn = true;
        updateLoginTime();
    }

    public void logout() {
        isLoggedIn = false;
        updateLogoutTime();
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
