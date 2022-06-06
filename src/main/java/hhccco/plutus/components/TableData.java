package hhccco.plutus.components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class TableData {
    private final TableView table = new TableView();
    private final String[] tableColumnsLabels = {"Movimento", "CC", "Versamento", "Prelevamento"};
    private final ArrayList<TableColumn> tableColumnsObjs = new ArrayList<>();

    public TableData() {
        initColumns();
        table.getColumns().addAll(tableColumnsObjs);
    }

    private void initColumns() {
        for(String columnLabel: tableColumnsLabels) {
            TableColumn column = new TableColumn(columnLabel);
            column.setCellValueFactory(new PropertyValueFactory<>(columnLabel.toLowerCase()));
            column.setPrefWidth(150);

            tableColumnsObjs.add(column);
        }
    }

    public TableView getTable() {
        return this.table;
    }
}
