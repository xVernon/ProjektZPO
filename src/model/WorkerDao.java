package model;

import java.sql.SQLException;
import java.util.List;

public interface WorkerDao {
    int add(Worker wor)
            throws SQLException;
    void delete(int id)
            throws SQLException;
    Worker getWorker(int id)
            throws SQLException;
    List<Worker> getWorker()
            throws SQLException;
    void update(Worker wor)
            throws SQLException;
}
