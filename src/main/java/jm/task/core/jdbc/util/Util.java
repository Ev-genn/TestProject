package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "54321");
                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                properties.setProperty("hibernate.show_sql", "false");
                properties.setProperty("hibernate.current_session_context_class", "thread");
                configuration.addProperties(properties)
                        .addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception ex) {
                System.out.println("Error sessionFactory");
            }
        }
        return sessionFactory;
    }



    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "test";
        String userName = "root";
        String password = "54321";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException {

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useSSL=false";
        Connection connection = DriverManager.getConnection(connectionURL, userName, password);
        return connection;
    }// реализуйте настройку соеденения с БД
}
