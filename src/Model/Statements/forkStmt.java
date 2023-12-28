package Model.Statements;

import Exceptions.MyException;
import Model.ADT.*;
import Model.PrgState;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.nio.Buffer;

public class forkStmt implements IStmt {
    IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    public PrgState execute(PrgState state) throws MyException {
        MyStack<IStmt> newStack = new MyStack<>();
        MyIDictionary<String, Value> newSymTable = state.getSymTable().deepCopy();
        MyIList<Value> outList = state.getOut();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        // the only one that is being deep copied is the symTable. This acts similar to processes in C
        return new PrgState(newStack, newSymTable, outList, fileTable, heap, stmt);
    }
}
