package Model.Statements;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIList;
import Exceptions.MyException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp e) {
        exp = e;
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> outList = state.getOut();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value val = exp.eval(symTbl, heap);
        outList.append(val);

        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
