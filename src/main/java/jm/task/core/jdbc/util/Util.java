package jm.task.core.jdbc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_CONNECTION_PROPERTIES = "./src/main/resources/db-connection.properties";

    public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        String url = null;
        String user = null;
        String pass = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(DB_CONNECTION_PROPERTIES))) {
            url = reader.readLine();
            user = reader.readLine();
            pass = reader.readLine();
            Class.forName(reader.readLine());
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
