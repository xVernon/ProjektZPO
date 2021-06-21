package model;

import java.sql.Date;
import java.time.LocalDate;

public class Visit {

    private int id;
    private int id_patient;
    private LocalDate date;
    private String reason;

    public Visit(){
    }

    public Visit(int id_patient, LocalDate date, String reason) {
        this.id_patient = id_patient;
        this.date = date;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) { this.date = date;}

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", id_pacjenta='" + id_patient + '\'' +
                ", data='" + date  +
                ", przyczyna='" + reason + '\'' +
                '}';
    }
}
