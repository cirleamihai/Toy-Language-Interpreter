package Model.Expression;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int op; // 1 - and, 2 - or

    public LogicExp(String operand, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;

        if (operand.equals("&&")) op = 1;
        else op = 2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {
                boolean i1, i2;
                i1 = ((BoolValue) v1).getVal();
                i2 = ((BoolValue) v2).getVal();

                if (op == 1) {
                    return new BoolValue(i1 && i2);
                } else {
                    return new BoolValue(i1 || i2);
                }
            } else {
                throw new MyException("second variable is not a boolean");
            }
        } else {
            throw new MyException("first variable is not a boolean");
        }
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);

        if (!typ1.equals(new BoolType()))
            throw new MyException("First operand is not a boolean");

        if (!typ2.equals(new BoolType()))
            throw new MyException("Second operand is not a boolean");

        return new BoolType();
    }

    public Exp deepCopy() {
        return new LogicExp(op == 1 ? "&&" : "||", e1.deepCopy(), e2.deepCopy());
    }
}
