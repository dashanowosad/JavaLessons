package ru.sibsutise;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DCL {
    public Statement statement;
    private Properties properties;
    private Connection connectionRoot;

    public DCL() throws IOException {
        this.properties = new Properties();
        this.properties.load(new FileReader("src/main/resources/root.properties"));

    }

    public void ComeToRoot() throws SQLException {
        //come to root
        connectionRoot = DriverManager.getConnection(
                properties.getProperty("db.host"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));
        statement = connectionRoot.createStatement();
    }

    public void GRANT_ON() throws SQLException {
        statement.executeUpdate("GRANT ALL ON user TO TestUser;");
    }
    public void REVOKE() throws SQLException {
        statement.executeUpdate("REVOKE ALL ON user FROM TestUser;");
    }
    public void SHOWDATABASES(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("Show Databases;");
        System.out.println("\nTABLES:");
        while(resultSet.next())
            System.out.println(resultSet.getString(1));
        System.out.println("\n");
    }
}
