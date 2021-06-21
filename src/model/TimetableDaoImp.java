package model;

import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableDaoImp implements TimetableDao {

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public int add(Timetable wor) throws SQLException {
        String query = "INSERT INTO list_of_timetable (data,id_pacjenta,id_lekarza) VALUES (?, ?, ?)";

        PreparedStatement ps  = con.prepareStatement(query);
        ps.setDate(1, Date.valueOf(wor.getDate()));
        //ps.setTime(2, wor.getTime());
        ps.setInt(2, wor.getId_patient());
        ps.setInt(3, wor.getId_doctor());

        int n = ps.executeUpdate();
        return n;
    }

    @Override
    public void delete(int id) throws SQLException { }

    @Override
    public Timetable getTimetable(int id) throws SQLException {

        String query = "SELECT * FROM list_of_timetable WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);
        Timetable wor = new Timetable();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            wor.setId(rs.getInt("id"));
            wor.setDate(rs.getDate("data").toLocalDate());
            //wor.setTime(rs.getTime("godzina"));
            wor.setId_patient(rs.getInt("id_pacjenta"));
            wor.setId_doctor(rs.getInt("id_lekarza"));
        }

        if (check == true) {
            return wor;
        }
        else
            return null;
    }

    @Override
    public List<Timetable> getTimetable() throws SQLException {
        String query = "select * from list_of_timetable";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Timetable> ls = new ArrayList();

        while (rs.next()) {
            Timetable wor = new Timetable();
            wor.setId(rs.getInt("id"));
            wor.setDate(rs.getDate("data").toLocalDate());
            //wor.setTime(rs.getTime("godzina"));
            wor.setId_patient(rs.getInt("id_pacjenta"));
            wor.setId_doctor(rs.getInt("id_lekarza"));
            ls.add(wor);
        }
        return ls;
    }

    @Override
    public void update(Timetable wor) throws SQLException {
        String query =  "update list_of_timetable set data=?, id_pacjenta=?, id_lekarza=? where id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setDate(1, Date.valueOf(wor.getDate()));
        //ps.setTime(2,wor.getTime());
        ps.setInt(2,wor.getId_patient());
        ps.setInt(3,wor.getId_doctor());
        ps.setInt(4, wor.getId());
        ps.executeUpdate();
    }
}
