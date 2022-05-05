package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    String sqlCreateTable = "CREATE TABLE IF NOT EXISTS USERS (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, firstName VARCHAR(45), lastName VARCHAR(45), age INT);";
    String sqlDropTable = "DROP TABLE IF EXISTS USERS";
    String sqlCleanTable = "TRUNCATE TABLE USERS";
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
