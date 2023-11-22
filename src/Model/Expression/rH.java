package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Value.RefValue;
import Model.Value.Value;

public class rH implements Exp{  // reads from the heap
    Exp exp;

    public rH(Exp e) {
        exp = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v = exp.eval(tbl, heap);

        if (v instanceof RefValue) {
            int addr = ((RefValue) v).getAddress();

            if (heap.contains(addr)) {
                return heap.get(addr);
            } else {
                throw new MyException("Address not found in heap!");
            }
        } else throw new MyException("Expression is not a reference type!");
    }

    @Override
    public Exp deepCopy() {
        return new rH(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
