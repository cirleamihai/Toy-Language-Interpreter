package View;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeapTableRowModel {
    private final SimpleIntegerProperty addresses = new SimpleIntegerProperty();
    private final SimpleStringProperty values = new SimpleStringProperty("");

    public HeapTableRowModel(Integer addresses, String values) {
        this.addresses.set(addresses);
        this.values.set(values);
    }

    public int getAddresses() {
        return addresses.get();
    }

    public SimpleIntegerProperty addressesProperty() {
        return addresses;
    }

    public void setAddresses(Integer addresses) {
        this.addresses.set(addresses);
    }

    public String getValues() {
        return values.get();
    }

    public SimpleStringProperty valuesProperty() {
        return values;
    }

    public void setValues(String values) {
        this.values.set(values);
    }
}

