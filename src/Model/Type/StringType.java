package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {
    public boolean equals(Object second) {
        return second instanceof StringType;
    }

    public String toString() {
        return "string";
    }

    public Value defaultValue() {
        return new StringValue("");
    }
}
