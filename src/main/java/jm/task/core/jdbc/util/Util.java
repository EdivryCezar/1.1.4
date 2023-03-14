package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {

        Connection connection = null;

        try {

            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
            }
        } catch (SQLException e) {
        }


        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/testdb");
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");


                Configuration config = new Configuration()
                        .setProperties(settings)
                        .addAnnotatedClass(User.class);


                ServiceRegistry serviceRegistry = new
                        StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties())
                        .build();

                sessionFactory = config.buildSessionFactory(serviceRegistry);


                System.out.println("_______БД подключена через Hibernate_______");
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Не могу настроить подключение к БД" + e);
            }
        }
        return sessionFactory;
    }
    /*public static SessionFactory getSessionFactory() {

        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL,"jdbc:mysql://localhost:3306/testdb");
            settings.put(Environment.USER, USERNAME);
            settings.put(Environment.PASS,PASSWORD);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration
                    .setProperties(settings)
                    .addAnnotatedClass(User.class);

            StandardServiceRegistry builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(builder);
            //System.out.println("Соединение установлено"); //РАБОТАЕТ!!!
        } catch (Exception e) {
            System.out.println("Что-то пошло не так...");
        }
        return sessionFactory;
    }
    /*private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/hibernate_db?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("That's okay");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }*/




    //SessionFactory sessionFactory = new Cofiguration().buildSessionFactory();
   /* private static SessionFactory sessionFactory;

    private Util() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    Properties properties = new Properties();
properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
properties.put(Environment.URL, "jdbc:mysql://localhost:3306/users");
properties.put(Environment.USER, "root");
properties.put(Environment.PASS, "root");

SessionFactory sessionFactory = new Configuration()
            .setProperties(properties)
            .buildSessionFactory();*/
}
