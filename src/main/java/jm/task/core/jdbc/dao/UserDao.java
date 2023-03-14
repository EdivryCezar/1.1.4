package jm.task.core.jdbc.dao;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    Object getAllUsers();

    void cleanUsersTable();
}
