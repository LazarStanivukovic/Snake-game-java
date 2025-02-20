package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class RowData {
    private List<SimpleStringProperty> columns;

    public RowData(List<String> values) {
        columns = new ArrayList<>();
        for (String value : values) {
            columns.add(new SimpleStringProperty(value));
        }
    }

    public String getColumn(int index) {
        return columns.get(index).get();
    }

    public void setColumn(int index, String value) {
        columns.get(index).set(value);
    }

    public SimpleStringProperty columnProperty(int index) {
        return columns.get(index);
    }

    public int getColumnCount() {
        return columns.size();
    }
}
