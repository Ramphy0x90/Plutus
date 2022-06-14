package hhccco.plutus.util;

import hhccco.plutus.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBconnection {
    Connection conn;
    public void checkDriver () {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());

            setConnection();
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "SQLite Drivers not started");
        }
    }

    private void setConnection() {
        String dbPrefix = "jdbc:sqlite:";
        String dbPath = String.valueOf(Main.class.getResource("database/plutus.db"));

        try {
            conn = DriverManager.getConnection(dbPrefix + dbPath);
        } catch (SQLException e) {
            System.err.println("SQLite connection error: \n\t" + e);
        }
    }
}
