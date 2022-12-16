package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static Util util;

    private Util(){}
    public static Util getInstance() {
        if (util == null) util = new Util();
        return util;
    }

    public Session getHibernateSession() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        return configuration.buildSessionFactory().getCurrentSession();
    }
}
