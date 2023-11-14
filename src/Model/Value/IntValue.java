package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {
    int val;
    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return Integer.toString(val);
    }

    public Type getType() {
        return new IntType();
    }

    public Value deepCopy() {
        return new IntValue(val);
    }
}
