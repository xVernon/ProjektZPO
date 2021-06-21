package model;

import service.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDaoImp implements WorkerDao{

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public int add(Worker wor) throws SQLException {
        String query = "INSERT INTO list_of_workers (name,surname,role,login,password) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps  = con.prepareStatement(query);
        ps.setString(1, wor.getName());
        ps.setString(2, wor.getSurname());
        ps.setString(3, wor.getRole());
        ps.setString(4, wor.getLogin());
        ps.setString(5, wor.getPassword());

        int n = ps.executeUpdate();
        return n;
    }

    @Override
    public void delete(int id) throws SQLException { }

    @Override
    public Worker getWorker(int id) throws SQLException {

        String query = "SELECT * FROM list_of_workers WHERE id= ?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);
        Worker wor = new Worker();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            wor.setId(rs.getInt("id"));
            wor.setName(rs.getString("name"));
            wor.setSurname(rs.getString("surname"));
            wor.setRole(rs.getString("role"));
            wor.setLogin(rs.getString("login"));
            wor.setPassword(rs.getString("password"));
        }

        if (check == true) {
            return wor;
        }
        else
            return null;
    }

    @Override
    public List<Worker> getWorker() throws SQLException {
        String query = "select * from list_of_workers";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Worker> ls = new ArrayList();

        while (rs.next()) {
            Worker wor = new Worker();
            wor.setId(rs.getInt("id"));
            wor.setName(rs.getString("name"));
            wor.setSurname(rs.getString("surname"));
            wor.setRole(rs.getString("role"));
            wor.setLogin(rs.getString("login"));
            wor.setPassword(rs.getString("password"));
            ls.add(wor);
        }
        return ls;
    }

    @Override
    public void update(Worker wor) throws SQLException {
        String query =  "update list_of_workers set name=?, surname=?, role=?, login=?, password=? where id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, wor.getName());
        ps.setString(2, wor.getSurname());
        ps.setString(3, wor.getRole());
        ps.setString(4, wor.getLogin());
        ps.setString(5, wor.getPassword());
        ps.setInt(6, wor.getId());
        ps.executeUpdate();
    }
}
