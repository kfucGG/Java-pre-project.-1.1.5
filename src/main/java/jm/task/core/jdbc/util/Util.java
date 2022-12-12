package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String SQL_URL = "jdbc:mysql://localhost:3306/new_schema";
    private final String SQL_USERNAME = "root";
    private final String SQL_PASSWORD = "root";

    public Connection getConnectionWithServer() {
        try {
            return DriverManager.getConnection(SQL_URL, SQL_USERNAME, SQL_PASSWORD);
        } catch(SQLException e) {
            System.out.println("Problem with connection to DB");
        }
        return null;
    }

    public Session getHibernateSession() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        return configuration.buildSessionFactory().getCurrentSession();
    }
}
