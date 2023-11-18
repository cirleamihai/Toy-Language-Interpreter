package Model.Expression;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Value.Value;

public class ValueExp implements Exp {
    Value val;

    public ValueExp (Value v) {
        val = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        return val;
    }

    public String toString() {
        return val.toString();
    }

    public Exp deepCopy() {
        return new ValueExp(val.deepCopy());
    }
}
