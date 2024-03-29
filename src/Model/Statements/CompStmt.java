package Model.Statements;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Exceptions.MyException;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
