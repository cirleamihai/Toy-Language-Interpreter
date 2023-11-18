package Model.Expression;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException;
    Exp deepCopy();
}
