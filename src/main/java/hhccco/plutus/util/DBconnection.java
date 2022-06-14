package hhccco.plutus.util;

import hhccco.plutus.Main;
import hhccco.plutus.models.TableDataModel;

import java.sql.*;
import java.util.Calendar;

public class DBconnection {
    Connection conn;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public DBconnection() {
        try {
            checkDriver();
            setConnection();
            createStruct();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("SQLite error: \n\t" + e);
        }
    }

    /**
     * Method to check sqlite drivers
     * @throws ClassNotFoundException If sqlite is not installed
     * @throws SQLException *
     */
    public void checkDriver () throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        DriverManager.registerDriver(new org.sqlite.JDBC());
    }

    /**
     * Method to load db file and create a connection
     * @throws SQLException if db file can't be found
     */
    private void setConnection() throws SQLException {
        String dbPrefix = "jdbc:sqlite:";
        String dbPath = String.valueOf(Main.class.getResource("database/plutus.db"));

        conn = DriverManager.getConnection(dbPrefix + dbPath);
    }

    /**
     *
     * @param tb Table name
     * @return boolean
     * @throws SQLException Error
     */
    private boolean checkIfTbExists(String tb) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        rs = md.getTables(null, null, tb, null);

        return rs.next();
    }

    /**
     * Method to create db tables
     * @throws SQLException general query error
     */
    private void createStruct() throws SQLException {
        Statement stmt = conn.createStatement();

        String movementTb = "CREATE TABLE movements" +
                "(date      DATE        NOT NULL," +
                "movement   TEXT        NOT NULL," +
                "cc         TEXT        NOT NULL," +
                "deposit    REAL         ," +
                "withdrawal REAL)";

        if(!checkIfTbExists("movements")) stmt.executeUpdate(movementTb);
        stmt.close();
    }

    private void devSampleData() throws SQLException {
        Statement stmt = conn.createStatement();

        int min = 123;
        int max = 1000000;

        int ranNum1 = (int) Math.floor(Math.random() * (min - max + 1) + min);
        int ranNum2 = (int) Math.floor(Math.random() * (min - max + 1) + min);

        for(int i = 0; i < 10; i++) {
            stmt.executeUpdate("INSERT INTO movements(date, movement, cc, deposit, withdrawal)" +
                    "VALUES('2022-01-01', 'Movement" + i + "', 'IDK', " + ranNum1 + ", " + ranNum2 + ")");
        }

        stmt.close();
    }

    public ResultSet getMovements() throws SQLException {
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM movements");

        return rs;
    }

    public void insertMovement(TableDataModel tableDataModel) throws SQLException {
        stmt = conn.prepareStatement("INSERT INTO movements(date, movement, cc, deposit, withdrawal)" +
                "VALUES(? ,?, ?, ? ,?)");

        stmt.setString(1, tableDataModel.getDate());
        stmt.setString(2, tableDataModel.getMovimento());
        stmt.setString(3, tableDataModel.getCc());
        stmt.setDouble(4, tableDataModel.getVersamento());
        stmt.setDouble(5, tableDataModel.getPrelevamento());

        stmt.executeUpdate();
    }
}
