package Model;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Model.ADT.MyIStack;
import Model.Statements.IStmt;
import Model.Value.Value;

public class PrgState {
    MyIStack<IStmt> stk;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIStack<IStmt> getStk() {
        return stk;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public PrgState (MyIStack<IStmt> stk, MyIDictionary<String, Value> symTable, MyIList<Value> out, IStmt originalProgram) {
        this.stk = stk;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        stk.push(originalProgram);
    }

    public String toString() {
        // we want to print the contents of the stack, the symbol table and the output list
        return "Stack: " + stk.toString() + "\n" +
                "Symbol Table: " + symTable.toString() + "\n" +
                "Output List: " + out.toString() + "\n";
    }
}
