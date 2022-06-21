package hhccco.plutus.components;

import hhccco.plutus.models.TableDataModel;
import hhccco.plutus.util.DBconnection;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class Body extends GridPane {
    public static final DBconnection dbConn = new DBconnection();
    static HashMap<String, Node> nodesObjects = new HashMap<>();
    private final GridPane centerColumnLayout = new GridPane();

    public Body() {
        this.setId("body");

        setStruct();
        initNodes();

        try {
            populateBanks();
            populateDataTable();
        } catch (SQLException e){
            System.err.println("SQLite error: \n\t" + e);
        }

        centerColumnLayout.add(nodesObjects.get("banksNavigation"), 0, 0);
        centerColumnLayout.add(nodesObjects.get("tableData"), 0, 1);

        this.add(nodesObjects.get("leftMenu"), 0, 0);
        this.add(centerColumnLayout, 1, 0);
    }

    private void setStruct() {
        RowConstraints mainRow = new RowConstraints();
        mainRow.setVgrow(Priority.ALWAYS);

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPrefWidth(200);

        ColumnConstraints centerColumn = new ColumnConstraints();
        centerColumn.setHgrow(Priority.ALWAYS);

        this.getRowConstraints().add(mainRow);
        this.getColumnConstraints().add(0, leftColumn);
        this.getColumnConstraints().add(1, centerColumn);

        ColumnConstraints mainColumn = new ColumnConstraints();
        mainColumn.setHgrow(Priority.ALWAYS);

        RowConstraints rowTop = new RowConstraints();

        RowConstraints rowBottom = new RowConstraints();
        rowBottom.setVgrow(Priority.ALWAYS);

        centerColumnLayout.getColumnConstraints().add(mainColumn);
        centerColumnLayout.getRowConstraints().add(rowTop);
        centerColumnLayout.getRowConstraints().add(rowBottom);
    }

    private void initNodes() {
        nodesObjects.put("leftMenu", new LeftMenu());
        nodesObjects.put("banksNavigation", new BanksHomeNavigation());
        nodesObjects.put("tableData", new TableData().getTable());
    }

    public static void populateDataTable(String ...date) throws SQLException {
        TableView tableData = ((TableView) nodesObjects.get("tableData"));
        tableData.getItems().clear();

        String movementsDate;

        if(date.length == 0) {
            movementsDate = dbConn.getMovementsLastDate();

            ((DatePicker) LeftMenu.nodesObjects.get("datePicker")).setValue(LocalDate.parse(movementsDate));
        } else {
            movementsDate = date[0];
        }

        String bankId = ((ComboBox<String>) BanksHomeNavigation.nodesObjects.get("bankPicker")).getValue();

        ResultSet rs = dbConn.getMovements(movementsDate, bankId);

        while(rs.next()) {
            TableDataModel newEntry = new TableDataModel(
                    LocalDate.parse(rs.getString("date")),
                    rs.getString("movement"),
                    rs.getString("cc"),
                    rs.getDouble("deposit"),
                    rs.getDouble("withdrawal"),
                    rs.getString("bankId"));

            tableData.getItems().add(newEntry);
        }
    }
    
    public static void populateBanks() throws SQLException {
        ComboBox<String> banksDropdown = (ComboBox<String>) BanksHomeNavigation.nodesObjects.get("bankPicker");
        TextField accountNumber = (TextField) BanksHomeNavigation.nodesObjects.get("accountNumberField");
        TextField accountType = (TextField) BanksHomeNavigation.nodesObjects.get("accountTypeField");
        boolean defaultBankSelected = false;

        banksDropdown.getItems().clear();

        ResultSet rs = dbConn.getBanks();

        while(rs.next()) {
            banksDropdown.getItems().add(rs.getString("name"));

            if(!defaultBankSelected) {
                banksDropdown.getSelectionModel().selectFirst();
                accountNumber.setText(rs.getString("accountNumber"));
                accountType.setText(rs.getString("accountType"));

                defaultBankSelected = true;
            }
        }
    }

    public static void setDataBySelectedBank(String bankName) throws SQLException {
        TextField accountNumber = (TextField) BanksHomeNavigation.nodesObjects.get("accountNumberField");
        TextField accountType = (TextField) BanksHomeNavigation.nodesObjects.get("accountTypeField");

        ResultSet rs = dbConn.getBanks(bankName);

        accountNumber.setText(rs.getString("accountNumber"));
        accountType.setText(rs.getString("accountType"));
    }
}