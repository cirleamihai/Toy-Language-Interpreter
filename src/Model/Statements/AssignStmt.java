package Model.Statements;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Exceptions.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {
    private final String id;
    private final Exp exp;

    public AssignStmt(String i, Exp e) {
        id = i;
        exp = e;
    }

    public String toString() {
        return id + "=" + exp.toString();
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTbl.contains(id)) {
            Value val = exp.eval(symTbl, heap);
            Type typeId = (symTbl.get(id)).getType();
            if (val.getType().equals(typeId)) {
                symTbl.put(id, val);
            } else {
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
            }
        } else {
            throw new MyException("Variable " + id + " not defined!");
        }
        return state;
    }

    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }
}
