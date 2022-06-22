package hhccco.plutus.util;

import hhccco.plutus.Main;
import hhccco.plutus.models.BankModel;
import hhccco.plutus.models.TableDataModel;

import java.sql.*;
import java.time.LocalDate;

public class DBconnection {
    Connection conn;
    PreparedStatement secureStmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    public DBconnection() {
        try {
            checkDriver();
            setConnection();
            createStruct();
            devSampleData();
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
        stmt = conn.createStatement();

        String banksTb = "CREATE TABLE banks" +
                "(name              TEXT        NOT NULL PRIMARY KEY," +
                "accountNumber      TEXT        NOT NULL," +
                "accountType        TEXT        NOT NULL)";

        String movementTb = "CREATE TABLE movements" +
                "(date          DATE        NOT NULL," +
                "movement       TEXT        NOT NULL," +
                "cc             TEXT        NOT NULL," +
                "deposit        REAL                ," +
                "withdrawal     REAL                ," +
                "bankId         TEXT        NOT NULL," +
                "FOREIGN KEY(bankId) REFERENCES banks(rowId))";

        if(!checkIfTbExists("movements")) stmt.executeUpdate(movementTb);
        if(!checkIfTbExists("banks")) stmt.executeUpdate(banksTb);

        stmt.close();
    }

    private void devSampleData() throws SQLException {
        stmt = conn.createStatement();

        int min = 123;
        int max = 1000000;

        int ranNum1 = (int) Math.floor(Math.random() * (min - max + 1) + min);
        int ranNum2 = (int) Math.floor(Math.random() * (min - max + 1) + min);

        for(int i = 0; i < 10; i++) {
            stmt.executeUpdate("INSERT INTO movements(date, movement, cc, deposit, withdrawal, bankId)" +
                    "VALUES('2022-01-01', 'Movement" + i + "', 'IDK', " + ranNum1 + ", " + ranNum2 + ", 'TEST')");
        }

        stmt.close();
    }

    public ResultSet getMovements(String date, String bankId) throws SQLException {
        secureStmt = conn.prepareStatement("SELECT rowId, * FROM movements WHERE date = ? AND bankId = ?");
        secureStmt.setString(1, date);
        secureStmt.setString(2, bankId);

        rs = secureStmt.executeQuery();

        return rs;
    }

    public ResultSet getBanks(String ...bankName) throws SQLException {
        if(bankName.length == 0) {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM banks");
        } else {
            secureStmt = conn.prepareStatement("SELECT * FROM banks WHERE name = ?");
            secureStmt.setString(1, bankName[0]);
            rs = secureStmt.executeQuery();
        }

        return rs;
    }

    public String getMovementsLastDate() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT date FROM movements ORDER BY date DESC LIMIT 1");

        return (rs.next()) ? rs.getString("date") : LocalDate.now().toString();
    }

    public void insertMovement(TableDataModel tableDataModel) throws SQLException {
        secureStmt = conn.prepareStatement("INSERT INTO movements(date, movement, cc, deposit, withdrawal, bankId)" +
                "VALUES(? ,?, ?, ? ,?, ?)");

        secureStmt.setString(1, tableDataModel.getDate());
        secureStmt.setString(2, tableDataModel.getMovimento());
        secureStmt.setString(3, tableDataModel.getCc());
        secureStmt.setDouble(4, tableDataModel.getVersamento());
        secureStmt.setDouble(5, tableDataModel.getPrelevamento());
        secureStmt.setString(6, tableDataModel.getBankId());

        secureStmt.executeUpdate();
    }

    public void updateMovement(TableDataModel tableDataModel) throws SQLException {
        secureStmt = conn.prepareStatement("UPDATE movements SET movement = ?, cc = ?, deposit = ?, withdrawal = ?, bankId = ? WHERE rowId = ?");
        System.out.println(tableDataModel.getId());
        secureStmt.setString(1, tableDataModel.getMovimento());
        secureStmt.setString(2, tableDataModel.getCc());
        secureStmt.setDouble(3, tableDataModel.getVersamento());
        secureStmt.setDouble(4, tableDataModel.getPrelevamento());
        secureStmt.setString(5, tableDataModel.getBankId());
        secureStmt.setInt(6, tableDataModel.getId());

        secureStmt.executeUpdate();
    }

    public void insertBank(BankModel bankModel) throws SQLException {
        secureStmt = conn.prepareStatement("INSERT INTO banks(name, accountNumber, accountType)" +
                "VALUES(? ,?, ?)");

        secureStmt.setString(1, bankModel.getName());
        secureStmt.setString(2, bankModel.getAccountNumber());
        secureStmt.setString(3, bankModel.getAccountType());

        secureStmt.executeUpdate();
    }

    public void updateBank(String oldBankId, BankModel bankModel) throws SQLException {
        secureStmt = conn.prepareStatement("UPDATE banks SET name = ?, accountNumber = ?, accountType = ? WHERE name = ?");

        secureStmt.setString(1, bankModel.getName());
        secureStmt.setString(2, bankModel.getAccountNumber());
        secureStmt.setString(3, bankModel.getAccountType());
        secureStmt.setString(4, oldBankId);

        secureStmt.executeUpdate();

        secureStmt = conn.prepareStatement("UPDATE movements SET bankId = ? WHERE bankId = ?");
        secureStmt.setString(1, bankModel.getName());
        secureStmt.setString(2, oldBankId);

        secureStmt.executeUpdate();
    }

    public void removeBank(String bankId) throws SQLException {
        secureStmt = conn.prepareStatement("DELETE FROM banks WHERE name = ?");
        secureStmt.setString(1, bankId);

        secureStmt.execute();
    }
}
