package ru.sibsutise;

import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
    private Statement statement;
    private ID id;

    public SQL(Statement statement, ID id){
        this.statement = statement;
        this.id = id;
    }
    public void add() throws SQLException {
        statement.executeUpdate("INSERT user(id,fio,phone) VALUES (" + this.id.id + ",'name','900');");
        this.id.id++;

    }

    public void AllDelete() throws SQLException {
        statement.executeUpdate("DELETE FROM user;");
        this.id.id = 0;
    }


}


class ID{
    int id = 0;
}