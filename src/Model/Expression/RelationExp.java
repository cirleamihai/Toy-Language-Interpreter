package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.Value.IntValue;

public class RelationExp implements Exp {
    Exp e1;
    Exp e2;
    String op;

    public RelationExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Exp deepCopy() {
        return new RelationExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + " " + op + " " + e2.toString() + ")";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value exp1_val = e1.eval(tbl, heap);
        Value exp2_val = e2.eval(tbl, heap);

        if (exp1_val.getType().equals(new IntType())) {
            if (exp2_val.getType().equals(new IntType())) {
                int n1 = ((IntValue)exp1_val).getVal();
                int n2 = ((IntValue)exp2_val).getVal();

                return switch (op) {
                    case "<" -> new BoolValue(n1 < n2);
                    case "<=" -> new BoolValue(n1 <= n2);
                    case "==" -> new BoolValue(n1 == n2);
                    case "!=" -> new BoolValue(n1 != n2);
                    case ">" -> new BoolValue(n1 > n2);
                    case ">=" -> new BoolValue(n1 >= n2);
                    default -> throw new MyException("Invalid operator!");
                };
            } else throw new MyException("Second operand is not an integer!");
        } else throw new MyException("First operand is not an integer!");
    }
}
