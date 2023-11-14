package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {
    public boolean equals(Object second) {
        return second instanceof IntType;
    }

    public String toString() {
        return "int";
    }

    public Value defaultValue() {
        return new IntValue(0);
    }
}
