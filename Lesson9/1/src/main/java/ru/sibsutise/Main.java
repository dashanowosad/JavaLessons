package ru.sibsutise;


import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static final void main(String ars[]) throws IOException, SQLException {
        Integer i;
        Long start, stop;

        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/app.properties"));

        //System.out.println(properties.get("db.host"));

        DCL dcl = new DCL();

        dcl.ComeToRoot(); //come to root
        dcl.GRANT_ON(); //add privileges to TestUser

        //come to TestUser
        Connection connection = DriverManager.getConnection(
                properties.getProperty("db.host"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));

        Statement statement = connection.createStatement();
        dcl.SHOWDATABASES(statement);
        ID id = new ID();
        SQL sql = new SQL(statement, id);


        sql.AllDelete();

        connection.setAutoCommit(false);
        start = System.currentTimeMillis();
        for(i = 0; i < 10; ++i)
            sql.add();
        connection.commit();
        stop = System.currentTimeMillis() - start;
        System.out.println("time without auto commit = " + stop + " miliseconds");

        sql.AllDelete();

        connection.setAutoCommit(true);
        start = System.currentTimeMillis();
        for(i = 0; i < 10; ++i)
            sql.add();
        stop = System.currentTimeMillis() - start;
        System.out.println("time with auto commit = " + stop + " miliseconds");

        dcl.ComeToRoot(); //come to root
        dcl.REVOKE();
        dcl.SHOWDATABASES(statement);
    }
}


