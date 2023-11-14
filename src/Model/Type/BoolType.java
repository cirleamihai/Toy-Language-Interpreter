package Model.Type;

import Model.Value.*;

public class BoolType implements Type{
    public boolean equals(Object second) {
        return second instanceof BoolType;
    }

    public String toString() {
        return "bool";
    }

    public Value defaultValue() {
        return new BoolValue(false);
    }
}
