package model;

import java.sql.SQLException;
import java.util.List;

public interface VisitDao {

    int add(Visit emp)
            throws SQLException;
    void delete(int id)
            throws SQLException;
    Visit getVisit(int id)
            throws SQLException;
    List<Visit> getVisit()
            throws SQLException;
    void update(Visit emp)
            throws SQLException;
}
