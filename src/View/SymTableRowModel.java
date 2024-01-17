package View;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymTableRowModel {
    private final SimpleStringProperty variableName;
    private final SimpleStringProperty value;

    public SymTableRowModel(String variableName, String value) {
        this.variableName = new SimpleStringProperty(variableName);
        this.value = new SimpleStringProperty(value);
    }

    public String getVariableName() {
        return variableName.get();
    }

    public SimpleStringProperty variableNameProperty() {
        return variableName;
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    // Optionally, setters...
}

