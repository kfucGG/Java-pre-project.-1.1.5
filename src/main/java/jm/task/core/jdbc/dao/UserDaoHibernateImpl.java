package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
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
        Session session = Util.getInstance().getHibernateSession();
        session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE user(\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    name varchar(30),\n" +
                    "    lastName varchar(30),\n" +
                    "    age int\n" +
                    ");").executeUpdate();
        } catch(PersistenceException ignore) {

        }
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getInstance().getHibernateSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getInstance().getHibernateSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getInstance().getHibernateSession();
        session.beginTransaction();

        session.remove(session.get(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getInstance().getHibernateSession();
        session.beginTransaction();
        List<User> allUsers = allUsers = session.createSQLQuery("SELECT * FROM user")
                .addScalar("id", new LongType())
                .addScalar("name", new StringType())
                .addScalar("lastName", new StringType())
                .addScalar("age", new ByteType())
                .setResultTransformer(Transformers.aliasToBean(User.class))
                .getResultList();
        session.getTransaction().commit();

        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getInstance().getHibernateSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE user").executeUpdate();
        session.getTransaction().commit();
    }
}
