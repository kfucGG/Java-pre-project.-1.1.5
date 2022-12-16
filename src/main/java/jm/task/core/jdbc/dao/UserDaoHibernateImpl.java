package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.CriteriaUpdateImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.ByteType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaUpdate;
import java.sql.SQLOutput;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = Util.getInstance().getHibernateSession();
            session.beginTransaction();
            try {
                session.createSQLQuery("CREATE TABLE user(\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    name varchar(30),\n" +
                        "    lastName varchar(30),\n" +
                        "    age int\n" +
                        ");").executeUpdate();
                session.getTransaction().commit();
            } catch (PersistenceException ignore) {

            }
        } catch(Exception e) {
            session.getTransaction().rollback();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = Util.getInstance().getHibernateSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        } catch(Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getInstance().getHibernateSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch(Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getInstance().getHibernateSession();
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch(Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Session session = null;
        try {
            session = Util.getInstance().getHibernateSession();
            session.getTransaction().begin();
            allUsers = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
        } catch(HibernateException e) {
            session.getTransaction().rollback();
        }

        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getInstance().getHibernateSession();
            session.getTransaction().begin();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch(Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
