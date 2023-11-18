package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public int getAddress() {
        return address;
    }

    public void setAddress(int a) {
        address = a;
    }

    public String toString() {
        return "(" + Integer.toString(address) + ", " + locationType.toString() + ")";
    }

    public RefValue(int a, Type t) {
        address = a;
        locationType = t;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    public Value deepCopy() {
        return new RefValue(address, new RefType(locationType));
    }

}
