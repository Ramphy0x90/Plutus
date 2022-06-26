package hhccco.plutus.util;

import hhccco.plutus.Main;
import hhccco.plutus.models.BankModel;
import hhccco.plutus.models.CcModel;
import hhccco.plutus.models.TableDataModel;

import java.sql.*;
import java.time.LocalDate;

public class DBConnection {
    Connection conn;
    PreparedStatement secureStmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    public DBConnection() {
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
     * Check if a table exists
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

        String ccTb = "CREATE TABLE cc" +
                "(cc            TEXT        NOT NULL PRIMARY KEY," +
                "description    TEXT        NOT NULL)";

        String banksTb = "CREATE TABLE banks" +
                "(name              TEXT        NOT NULL PRIMARY KEY," +
                "accountNumber      TEXT        NOT NULL," +
                "accountType        TEXT        )";

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
        if(!checkIfTbExists("cc")) stmt.executeUpdate(ccTb);

        stmt.close();
    }

    /**
     * Method to get all movements
     * @param date get movements from a selected date
     * @param bankId get movements from a selected bank
     * @return result set
     * @throws SQLException *
     */
    public ResultSet getMovements(String date, String bankId) throws SQLException {
        secureStmt = conn.prepareStatement("SELECT rowId, * FROM movements WHERE date = ? AND bankId = ?");
        secureStmt.setString(1, date);
        secureStmt.setString(2, bankId);

        rs = secureStmt.executeQuery();

        return rs;
    }

    /**
     * Get banks
     * @param bankName if this parameter is specified then return the specific bank
     * @return result set
     * @throws SQLException *
     */
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

    /**
     * Get CCs
     * @param cc if this parameter is specified then return the specific CC
     * @return result set
     * @throws SQLException *
     */
    public ResultSet getCcs(String ...cc) throws SQLException {
        if(cc.length == 0) {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cc");
        } else {
            secureStmt = conn.prepareStatement("SELECT * FROM cc WHERE cc = ?");
            secureStmt.setString(1, cc[0]);
            rs = secureStmt.executeQuery();
        }

        return rs;
    }

    /**
     * Get the date of the last movement
     * @return String of the date
     * @throws SQLException *
     */
    public String getMovementsLastDate() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT date FROM movements ORDER BY date DESC LIMIT 1");

        return (rs.next()) ? rs.getString("date") : LocalDate.now().toString();
    }

    /**
     * Insert new movement
     * @param tableDataModel model with data
     * @throws SQLException *
     */
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

        secureStmt.close();
    }

    /**
     * Update movement
     * @param tableDataModel model with new data
     * @throws SQLException *
     */
    public void updateMovement(TableDataModel tableDataModel) throws SQLException {
        secureStmt = conn.prepareStatement("UPDATE movements SET movement = ?, cc = ?, deposit = ?, withdrawal = ?, bankId = ? WHERE rowId = ?");

        secureStmt.setString(1, tableDataModel.getMovimento());
        secureStmt.setString(2, tableDataModel.getCc());
        secureStmt.setDouble(3, tableDataModel.getVersamento());
        secureStmt.setDouble(4, tableDataModel.getPrelevamento());
        secureStmt.setString(5, tableDataModel.getBankId());
        secureStmt.setInt(6, tableDataModel.getId());

        secureStmt.executeUpdate();

        secureStmt.close();
    }

    /**
     * Remove movement
     * @param movementId movement id to remove
     * @throws SQLException *
     */
    public void removeMovement(int movementId) throws SQLException {
        secureStmt = conn.prepareStatement("DELETE FROM movements WHERE rowId = ?");
        secureStmt.setInt(1, movementId);

        secureStmt.execute();

        secureStmt.close();
    }

    /**
     * Insert new bank
     * @param bankModel model with data
     * @throws SQLException *
     */
    public void insertBank(BankModel bankModel) throws SQLException {
        secureStmt = conn.prepareStatement("INSERT INTO banks(name, accountNumber, accountType)" +
                "VALUES(? ,?, ?)");

        secureStmt.setString(1, bankModel.getName());
        secureStmt.setString(2, bankModel.getAccountNumber());
        secureStmt.setString(3, bankModel.getAccountType());

        secureStmt.executeUpdate();

        secureStmt.close();
    }

    /**
     * Update bank data
     * @param oldBankId old bank name
     * @param bankModel model with new data
     * @throws SQLException *
     */
    public void updateBank(String oldBankId, BankModel bankModel) throws SQLException {
        secureStmt = conn.prepareStatement("UPDATE banks SET name = ?, accountNumber = ?, accountType = ? WHERE name = ?");

        secureStmt.setString(1, bankModel.getName());
        secureStmt.setString(2, bankModel.getAccountNumber());
        secureStmt.setString(3, bankModel.getAccountType());
        secureStmt.setString(4, oldBankId);

        secureStmt.executeUpdate();

        secureStmt.close();

        secureStmt = conn.prepareStatement("UPDATE movements SET bankId = ? WHERE bankId = ?");
        secureStmt.setString(1, bankModel.getName());
        secureStmt.setString(2, oldBankId);

        secureStmt.executeUpdate();

        secureStmt.close();
    }

    /**
     * Remove bank
     * @param bankId bank name
     * @throws SQLException *
     */
    public void removeBank(String bankId) throws SQLException {
        secureStmt = conn.prepareStatement("DELETE FROM banks WHERE name = ?");
        secureStmt.setString(1, bankId);

        secureStmt.execute();

        secureStmt.close();
    }

    /**
     * Insert new CC
     * @param ccModel model with data
     * @throws SQLException *
     */
    public void insertCc(CcModel ccModel) throws SQLException {
        secureStmt = conn.prepareStatement("INSERT INTO cc(cc, description)" +
                "VALUES(? ,?)");

        secureStmt.setString(1, ccModel.getCc());
        secureStmt.setString(2, ccModel.getDescription());

        secureStmt.executeUpdate();

        secureStmt.close();
    }

    /**
     * Update CC
     * @param oldCc old CC name
     * @param ccModel model with new data
     * @throws SQLException *
     */
    public void updateCc(String oldCc, CcModel ccModel) throws SQLException {
        secureStmt = conn.prepareStatement("UPDATE cc SET cc = ?, description = ? WHERE cc = ?");

        secureStmt.setString(1, ccModel.getCc());
        secureStmt.setString(2, ccModel.getDescription());
        secureStmt.setString(3, oldCc);

        secureStmt.executeUpdate();

        secureStmt.close();

        secureStmt = conn.prepareStatement("UPDATE movements SET cc = ? WHERE cc = ?");
        secureStmt.setString(1, ccModel.getCc());
        secureStmt.setString(2, oldCc);

        secureStmt.executeUpdate();

        secureStmt.close();
    }

    /**
     * Remove CC
     * @param cc cc name
     * @throws SQLException *
     */
    public void removeCc(String cc) throws SQLException {
        secureStmt = conn.prepareStatement("DELETE FROM cc WHERE cc = ?");
        secureStmt.setString(1, cc);

        secureStmt.execute();

        secureStmt.close();
    }
}
