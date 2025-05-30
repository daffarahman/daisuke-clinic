import java.time.LocalDateTime;

public class Doctor {
    private int Id;
    private String name;
    private String specialty;
    private LocalDateTime loginTime;

    public Doctor(int id, String name, String specialty) {
        this.Id = id;
        this.name = name;
        this.specialty = specialty;
        this.loginTime = LocalDateTime.now();
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
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
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }
}
