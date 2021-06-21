package model;

import service.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImp implements PatientDao{

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public int add(Patient emp) throws SQLException {
        String query = "INSERT INTO list_of_patient (name,surname,sex,birthday,pesel,city,street,house_number,local_number,phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps  = con.prepareStatement(query);
        ps.setString(1, emp.getName());
        ps.setString(2, emp.getSurname());
        ps.setString(3,emp.getSex());
        ps.setDate(4, Date.valueOf(emp.getBirthday()));
        ps.setString(5, emp.getPesel());
        ps.setString(6, emp.getCity());
        ps.setString(7, emp.getStreet());
        ps.setString(8, emp.getHouseNumber());
        ps.setString(9, emp.getLocalNumber());
        ps.setString(10, emp.getTelephoneNumber());

        int n = ps.executeUpdate();
        return n;
    }

    @Override
    public void delete(int id) throws SQLException { }

    @Override
    public Patient getPatient(int id) throws SQLException {

        String query = "SELECT * FROM list_of_patient WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);
        Patient emp = new Patient();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setSurname(rs.getString("surname"));
            emp.setBirthday(rs.getDate("birthday").toLocalDate());
            emp.setCity(rs.getString("city"));
            emp.setHouseNumber(rs.getString("house_number"));
            emp.setSex(rs.getString("sex"));
            emp.setPesel(rs.getString("pesel"));
            emp.setTelephoneNumber(rs.getString("phone_number"));
            emp.setLocalNumber(rs.getString("local_number"));
            emp.setStreet(rs.getString("street"));
        }

        if (check == true) {
            return emp;
        }
        else
            return null;
    }

    @Override
    public List<Patient> getPatients() throws SQLException {
        String query = "select * from list_of_patient";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Patient> ls = new ArrayList();

        while (rs.next()) {
            Patient emp = new Patient();
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setSurname(rs.getString("surname"));
            emp.setBirthday(rs.getDate("birthday").toLocalDate());
            emp.setCity(rs.getString("city"));
            emp.setHouseNumber(rs.getString("house_number"));
            emp.setSex(rs.getString("sex"));
            emp.setPesel(rs.getString("pesel"));
            emp.setTelephoneNumber(rs.getString("phone_number"));
            emp.setLocalNumber(rs.getString("local_number"));
            emp.setStreet(rs.getString("street"));
            ls.add(emp);
        }
        return ls;
    }

    @Override
    public void update(Patient emp) throws SQLException {
       String query =  "update list_of_patient set name=?, surname=?, birthday=?, pesel=?, " +
               "city=?, house_number=?, local_number=?, phone_number=?, street=? where id = ?";
       PreparedStatement ps = con.prepareStatement(query);
       ps.setString(1, emp.getName());
       ps.setString(2, emp.getSurname());
       ps.setDate(3,Date.valueOf(emp.getBirthday()));
       ps.setString(4,emp.getPesel());
       ps.setString(5,emp.getCity());
       ps.setString(6, emp.getHouseNumber());
       ps.setString(7, emp.getLocalNumber());
       ps.setString(8, emp.getTelephoneNumber());
       ps.setString(9, emp.getStreet());
       ps.setInt(10, emp.getId());
       ps.executeUpdate();
    }
}
