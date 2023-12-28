package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;

public class whileStatement implements IStmt {
    Exp exp;
    IStmt stmt;

    public whileStatement(Exp e, IStmt s) {
        exp = e;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIStack<IStmt> stk = state.getStk();
        Value exp_val = exp.eval(symTable, heap);

        if (!exp_val.getType().equals(new BoolType()))
            throw new MyException("The condition from while statement is not a boolean");

        BoolValue bool_exp_val = (BoolValue) exp_val;
        if (!bool_exp_val.getVal()) {
            return null; // we don't push anything on the stack and return
        }

        // otherwise we push the while statement again on the stack and then the statement
        stk.push(deepCopy());
        stk.push(stmt);

        return null;
    }

    @Override
    public String toString() {
        return "while(" + exp.toString() + ") " + stmt.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new whileStatement(exp.deepCopy(), stmt.deepCopy());
    }
}
