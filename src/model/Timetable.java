package model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Timetable {

    private int id;
    private LocalDate date;
    private int id_patient;
    private int id_doctor;

    public Timetable() {
    }

    public Timetable(LocalDate date, int id_patient, int id_doctor) {
        this.date = date;
        this.id_patient = id_patient;
        this.id_doctor = id_doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

        @Override
        public String toString() {
            return "Timetable{" +
                    "id=" + id +
                    ", data='" + date +
                    ", id_pacjenta='" + id_patient + '\'' +
                    ", id_lekarza=" + id_doctor + '\'' +
                    '}';
    }
}
