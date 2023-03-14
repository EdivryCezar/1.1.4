package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE  IF NOT EXISTS users (\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\n" + "  `name` VARCHAR(45) NOT NULL,\n" + "  `lastName` VARCHAR(45) NOT NULL,\n" + "  `age` INT NULL,\n" + "  PRIMARY KEY (`id`));")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка создания");
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка чего-то там");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Ошибка сохранения");
        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь с id " + id + " удален");
        } catch (Exception e) {
            System.out.println("Ошибка удаления пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List userList = session.createQuery("from User").getResultList();
            transaction.commit();
           for (Object u : userList) {
                System.out.println(u);
            }
            return userList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.out.println("Ошибка очистки таблицы");
        }
    }
}
