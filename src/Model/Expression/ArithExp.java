package Model.Expression;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.Type.IntType;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; // 1 - plus, 2 - minus, 3 - multiply, 4 - divide
    char operand;

    public ArithExp(char operand, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.operand = operand;

        if (operand == '+') op = 1;
        else if (operand == '-') op = 2;
        else if (operand == '*') op = 3;
        else op = 4;
    }

    public String toString() {

        return e1.toString() + " " + operand + " " + e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl);
        v2 = e2.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            if (v2.getType().equals(new IntType())) {
                int i1, i2;
                i1 = ((IntValue) v1).getVal();
                i2 = ((IntValue) v2).getVal();

                if (op == 1) return new IntValue(i1 + i2);
                else if (op == 2) return new IntValue(i1 - i2);
                else if (op == 3) return new IntValue(i1 * i2);
                else {
                    if (i2 == 0) throw new MyException("division by zero");
                    else return new IntValue(i1 / i2);
                }
            } else {
                throw new MyException("second variable is not an integer");
            }
        } else {
            throw new MyException("first variable is not an integer");
        }
    }

    public Exp deepCopy() {
        return new ArithExp(operand, e1.deepCopy(), e2.deepCopy());
    }
}
