package model;

import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitDaoImp implements VisitDao {

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public int add(Visit wor) throws SQLException {
        String query = "INSERT INTO list_of_visits (id_pacjenta,data,przyczyna) VALUES (?, ?, ?)";

        PreparedStatement ps  = con.prepareStatement(query);
        ps.setInt(1, wor.getId_patient());
        ps.setDate(2, Date.valueOf(wor.getDate()));
        ps.setString(3, wor.getReason());

        int n = ps.executeUpdate();
        return n;
    }

    @Override
    public void delete(int id) throws SQLException { }

    @Override
    public Visit getVisit(int id) throws SQLException {

        String query = "SELECT * FROM list_of_visits WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);
        Visit wor = new Visit();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            wor.setId(rs.getInt("id"));
            wor.setId_patient(rs.getInt("id_pacjenta"));
            wor.setDate(rs.getDate("data").toLocalDate());
            wor.setReason(rs.getString("przyczyna"));
        }

        if (check == true) {
            return wor;
        }
        else
            return null;
    }

    @Override
    public List<Visit> getVisit() throws SQLException {
        String query = "select * from list_of_visits";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Visit> ls = new ArrayList();

        while (rs.next()) {
            Visit wor = new Visit();
            wor.setId(rs.getInt("id"));
            wor.setId_patient(rs.getInt("id_pacjenta"));
            wor.setDate(rs.getDate("data").toLocalDate());
            wor.setReason(rs.getString("przyczyna"));
            ls.add(wor);
        }
        return ls;
    }

    @Override
    public void update(Visit wor) throws SQLException {
        String query =  "update list_of_visits set id_pacjenta=?, data=?, przyczyna=? where id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,wor.getId_patient());
        ps.setDate(2, Date.valueOf(String.valueOf(wor.getDate())));
        ps.setString(3,wor.getReason());
        ps.setInt(4, wor.getId());
        ps.executeUpdate();
    }
}
