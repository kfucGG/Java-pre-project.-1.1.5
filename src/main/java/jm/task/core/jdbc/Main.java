package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {


    private UserServiceImpl userService = new UserServiceImpl();;

    public static void main(String[] args) {
        Main main = new Main();
        main.createTable();
        User user = main.saveUser("test", "test", (byte)2);
        System.out.println(user.toString() + "added in console!");
        User user2 = main.saveUser("test2", "test", (byte)2);
        System.out.println(user2.toString() + "added in console!");
        User user3 = main.saveUser("test3", "test", (byte)2);
        System.out.println(user3.toString() + "added in console!");
        User user4 = main.saveUser("test4", "test", (byte)2);
        System.out.println(user4.toString() + "added in console!");
        System.out.println(main.findAll().toString());
        main.truncateTable();
        main.deleteTable();
    }

    public void createTable() {
        userService.createUsersTable();
    }
    public void deleteTable() {
        userService.dropUsersTable();
    }
    public void truncateTable() {
        userService.cleanUsersTable();
    }
    public List<User> findAll() {
        return userService.getAllUsers();
    }
    public User saveUser(String name, String lastName, byte b) {
        User user = new User(name, lastName, b);
        userService.saveUser(name, lastName, b);
        return user;
    }
}
