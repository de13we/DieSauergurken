package de.fhdw.chitter;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private Connection con;
    private Statement statement;

    public DatabaseConnection() throws SQLException, ClassNotFoundException {
        initialize();
    }

    private void initialize() throws ClassNotFoundException, SQLException {
        // Treiber laden und verbinden
        Class c = Class.forName("org.h2.Driver");
        con = DriverManager.getConnection(String.format("jdbc:h2:file:%s\\database\\database", System.getProperty("user.dir")), "user","");

        // Statement Objekt erstellen
        statement = con.createStatement();

        createStaff();
        createTopics();
    }

    // Erstellt die Staff Tabelle
    private void createStaff() throws SQLException {
        // Tabelle erstellen
        String sql_create = "CREATE TABLE IF NOT EXISTS staff(id INTEGER auto_increment, name VARCHAR(50), passwort VARCHAR(50), PRIMARY KEY (name))";
        statement.execute(sql_create);
        addStaff(new Staff("Max", ""));
        addStaff(new Staff("Hans", "12345"));
        addStaff(new Staff("John", "wer?"));
    }

    // Fügt einen Mitarbeiter hinzu
    public void addStaff(Staff staff) throws SQLException {
        // Datensatz einfügen
        try {
            // Datensatz einfügen in database
            String sql_insert = String.format("INSERT INTO staff(name, passwort) VALUES('%s', '%s')", staff.getName(), staff.getPassword());
            statement.execute(sql_insert);
        }catch (JdbcSQLIntegrityConstraintViolationException e) {
            System.out.println("Topic existiert bereits; kann nicht eingefügt werden");
        }
    }

    public List<Staff> getStaff() throws SQLException {
        String sql_select = "SELECT * FROM staff";
        List<Staff> staffResultList = new ArrayList<>();
        ResultSet rs = statement.executeQuery(sql_select);
        while(rs.next()){
            staffResultList.add(new Staff(rs.getString(2), rs.getString(3)));
        }

        return staffResultList;
    }

    // Erstellt die Topics Tabelle
    private void createTopics() throws SQLException {
        String sql_create = "CREATE TABLE IF NOT EXISTS topic(name VARCHAR(50), PRIMARY KEY (name))";
        statement.execute(sql_create);
        addTopic("Politik");
        addTopic("Sport");
        addTopic("Wirtschaft");
    }

    // Fügt ein Topic hinzu
    public void addTopic(String topic) throws SQLException {
        try {
            // Datensatz einfügen in database
            String sql_insert = String.format("INSERT INTO topic(name) VALUES('%s')", topic);
            statement.execute(sql_insert);
        }catch (org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException e) {
            System.out.println("Topic existiert bereits; kann nicht eingefügt werden");
        }
    }

    public List<String> getTopics() throws SQLException {
        String sql_select = "SELECT * FROM topic";
        List<String> topicList = new ArrayList<>();
        ResultSet rs = statement.executeQuery(sql_select);
        while(rs.next()){
            topicList.add(rs.getString(1));
        }
        return topicList;
    }
}
