package hhccco.plutus.components;

import hhccco.plutus.models.TableDataModel;
import hhccco.plutus.util.DBconnection;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Body extends GridPane {
    public static final DBconnection dbConn = new DBconnection();
    static HashMap<String, Node> nodesObjects = new HashMap<>();

    public Body() {
        this.setId("body");

        setStruct();
        initNodes();

        try {
            populateDataTable();
        } catch (SQLException e){
            System.err.println("SQLite error: \n\t" + e);
        }

        this.add(nodesObjects.get("leftMenu"), 0, 0);
        this.add(nodesObjects.get("tableData"), 1, 0);
    }

    private void setStruct() {
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPrefWidth(200);

        ColumnConstraints centerColumn = new ColumnConstraints();
        centerColumn.setHgrow(Priority.ALWAYS);

        RowConstraints mainRow = new RowConstraints();
        mainRow.setVgrow(Priority.ALWAYS);

        this.getRowConstraints().add(mainRow);
        this.getColumnConstraints().add(0, leftColumn);
        this.getColumnConstraints().add(1, centerColumn);
    }

    private void initNodes() {
        nodesObjects.put("tableData", new TableData().getTable());
        nodesObjects.put("leftMenu", new LeftMenu());
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

        ResultSet rs = dbConn.getMovements(movementsDate);

        while(rs.next()) {
            TableDataModel newEntry = new TableDataModel(
                    LocalDate.parse(rs.getString("date")),
                    rs.getString("movement"),
                    rs.getString("cc"),
                    rs.getDouble("deposit"),
                    rs.getDouble("withdrawal")
            );

            tableData.getItems().add(newEntry);
        }
    }
}