package jm.task.core.jdbc.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class Util {

    private static final String DB_CONNECTION_PROPERTIES = "./src/main/resources/db-connection.properties";

    public static Connection getConnection() throws SQLException {
        String url = null;
        String user = null;
        String pass = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(DB_CONNECTION_PROPERTIES))) {
            url = reader.readLine();
            user = reader.readLine();
            pass = reader.readLine();
            Class.forName(reader.readLine());
        } catch (FileNotFoundException e) {
            log.warn("File with database connection properties was not found:" + e.getMessage());
        } catch (IOException e) {
            log.warn("Couldn't read database connection properties:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            log.warn("No database connection driver was found" + e.getMessage());
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
