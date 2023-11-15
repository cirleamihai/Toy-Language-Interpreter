package Model;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Model.ADT.MyIStack;
import Model.Statements.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> stk;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
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

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public PrgState (MyIStack<IStmt> stk, MyIDictionary<String, Value> symTable,
                     MyIList<Value> out, MyIDictionary<StringValue, BufferedReader> fileTable, IStmt originalProgram) {
        this.stk = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        stk.push(originalProgram);
    }

    public String toString() {
        // we want to print the contents of the stack, the symbol table and the output list
        return "Stack: " + stk.toString() + "\n" +
                "Symbol Table: " + symTable.toString() + "\n" +
                "Output List: " + out.toString() + "\n" +
                "File Table: " + fileTable.toString() + "\n";
    }

    public String toFile() {
        // ANSI escape codes for colors and text styles
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BOLD = "\u001B[1m";

        return ANSI_BOLD + ANSI_RED + "ExeStack: " + ANSI_RESET + stk.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "SymTable: " + ANSI_RESET + symTable.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "OutList: " + ANSI_RESET + out.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "FileTable: " + ANSI_RESET + fileTable.toFile() + "\n";
    }
}
