package Model.Expression;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl) throws MyException;
    Exp deepCopy();
}
