package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {
    boolean val;
    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {
        return Boolean.toString(val);
    }

    public Type getType() {
        return new BoolType();
    }

    public Value deepCopy() {
        return new BoolValue(val);
    }
}
