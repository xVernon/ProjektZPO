package model;

import java.sql.SQLException;
import java.util.List;

public interface PatientDao
{
    int add(Patient emp)
            throws SQLException;
    void delete(int id)
            throws SQLException;
    Patient getPatient(int id)
            throws SQLException;
    List<Patient> getPatients()
            throws SQLException;
    void update(Patient emp)
            throws SQLException;
}
