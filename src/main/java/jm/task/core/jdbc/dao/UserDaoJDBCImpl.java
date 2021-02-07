package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection()) {
            String sql = "CREATE TABLE user " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " age TINYINT UNSIGNED not NULL)";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Таблица уже существует");
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection()) {
            String sql = "DROP TABLE user ";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Таблицы уже нет");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getMySQLConnection()) {
            String sql = "INSERT INTO user(name, lastname, age) VALUE (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
            System.out.println( "User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Ошибка добавления User");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getMySQLConnection()) {
            String sql = "DELETE FROM user WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Такой строки нет");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection()) {
            String sql = "SELECT id, name, lastName, age FROM user";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Список не свормирован");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getMySQLConnection()) {
            String sql = "TRUNCATE TABLE user ";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Таблица не очищена");
        }
    }
}
