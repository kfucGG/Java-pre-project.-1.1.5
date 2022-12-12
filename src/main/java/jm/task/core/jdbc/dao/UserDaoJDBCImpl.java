package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util;
    public UserDaoJDBCImpl() {
        util = new Util();
    }

    public void createUsersTable() {
        try (Connection connection = util.getConnectionWithServer()){
            connection.createStatement().executeUpdate("CREATE TABLE user(\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    name varchar(30),\n" +
                    "    lastname varchar(30),\n" +
                    "    age int\n" +
                    ");");
        } catch(SQLException ignore) {

        }
    }

    public void dropUsersTable() {

        try (Connection connection = util.getConnectionWithServer()){
            connection.createStatement().executeUpdate("DROP TABLE user;");
        } catch (SQLException ignore) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = util.getConnectionWithServer()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO user(name, lastName, age) values(?, ?, ?);");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem with adding user in table");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnectionWithServer()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM user where id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = util.getConnectionWithServer()) {
            ResultSet result = connection.createStatement().executeQuery("SELECT * FROM user");
            while(result.next() != false) {
                allUsers.add(new User(result.getLong(1),
                        result.getString(2)
                        , result.getString(3),
                        result.getByte(4)));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnectionWithServer()) {
            connection.createStatement().executeUpdate("TRUNCATE user");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}


