package View;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeapTableRowModel {
    private final SimpleIntegerProperty column1 = new SimpleIntegerProperty();
    private final SimpleStringProperty column2 = new SimpleStringProperty("");

    public HeapTableRowModel(Integer col1, String col2) {
        this.column1.set(col1);
        this.column2.set(col2);
    }

    public Integer getColumn1() {
        return column1.get();
    }

    public SimpleIntegerProperty column1Property() {
        return column1;
    }

    public void setColumn1(Integer column1) {
        this.column1.set(column1);
    }

    public String getColumn2() {
        return column2.get();
    }

    public SimpleStringProperty column2Property() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2.set(column2);
    }
}
