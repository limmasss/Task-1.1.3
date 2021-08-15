package jm.task.core.jdbc.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_CONNECTION_PROPERTIES = "./src/main/resources/db-connection.properties";

    public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(DB_CONNECTION_PROPERTIES));
        String url = reader.readLine();
        String user = reader.readLine();
        String pass = reader.readLine();
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}
