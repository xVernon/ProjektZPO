package model;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface TimetableDao {

        int add(Timetable emp)
                throws SQLException;
        void delete(int id)
                throws SQLException;
        Timetable getTimetable(int id)
                throws SQLException;
        List<Timetable> getTimetable()
                throws SQLException;
        void update(Timetable emp)
                throws SQLException;

}
