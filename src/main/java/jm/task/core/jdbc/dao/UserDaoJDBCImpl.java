package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE_SCRIPT= "./src/main/resources/create-users-table.sql";
    private static final String DROP_TABLE_SCRIPT= "./src/main/resources/drop-users-table.sql";
    private static final String CLEAN_TABLE_SCRIPT= "./src/main/resources/clean-users-table.sql";

    private static final String SAVE_USER_SQL = "insert into users(name, lastname, age) values (?, ?, ?);";
    private static final String REMOVE_USER_SQL = "delete from users where id = ?";
    private static final String GET_ALL_USER_SQL = "select * from users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(new FileReader(CREATE_TABLE_SCRIPT));
        } catch (SQLException e) {
            log.warn("Failed when executing CREATE_TABLE_SCRIPT: " + e.getMessage());
        } catch (IOException e) {
            log.warn("Failed while loading SQL script:" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(new FileReader(DROP_TABLE_SCRIPT));
        } catch (SQLException e) {
            log.warn("Failed when executing DROP_TABLE_SCRIPT: " + e.getMessage());
        } catch (IOException e) {
            log.warn("Failed while loading SQL script:" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement saveUserStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            saveUserStatement.setString(1, name);
            saveUserStatement.setString(2, lastName);
            saveUserStatement.setByte(3, age);
            saveUserStatement.execute();
        } catch (SQLException e) {
            log.warn("Failed when executing SAVE_USER_SQL: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
            PreparedStatement removeUserStatement = connection.prepareStatement(REMOVE_USER_SQL)) {
            removeUserStatement.setLong(1, id);
            removeUserStatement.execute();
        } catch (SQLException e) {
            log.warn("Failed when executing REMOVE_USER_SQL: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            Statement getAllUsersStatement = connection.createStatement();
            ResultSet allUsers = getAllUsersStatement.executeQuery(GET_ALL_USER_SQL)) {
            while (allUsers.next()) {
                users.add(new User(allUsers.getString("name"),
                        allUsers.getString("lastname"),
                        allUsers.getByte("age")));
            }
        } catch (SQLException e) {
            log.warn("Failed when executing GET_ALL_USERS_SQL: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(new FileReader(CLEAN_TABLE_SCRIPT));
        } catch (SQLException e) {
            log.warn("Failed when executing CLEAN_TABLE_SCRIPT: " + e.getMessage());
        } catch (IOException e) {
            log.warn("Failed while loading SQL script:" + e.getMessage());
        }
    }
}
