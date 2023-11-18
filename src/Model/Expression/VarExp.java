package Model.Expression;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String i) {
        id = i;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        return tbl.get(id);
    }

    public String toString() {
        return id;
    }

    public Exp deepCopy() {
        return new VarExp(id);
    }
}
