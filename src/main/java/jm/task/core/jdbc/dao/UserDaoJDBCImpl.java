package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("    CREATE TABLE  IF NOT EXISTS users (\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\n" + "  `name` VARCHAR(45) NOT NULL,\n" + "  `lastName` VARCHAR(45) NOT NULL,\n" + "  `age` INT NULL,\n" + "  PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            System.out.println("Чего?");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = getConnection().prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Шо происходит?");
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления по id");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * from users")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Треш какой-то...");
        }
        return userList;//вывод всех пользователей из БД
    }

    public void cleanUsersTable() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы");
        }
    }
}
