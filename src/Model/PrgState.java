package Model;

import Exceptions.MyException;
import Model.ADT.*;
import Model.Statements.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> stk;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Integer, Value> heap;
    IStmt originalProgram;
    Integer id;
    static Integer idCounter = 0;

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

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public Integer getId() {
        return id;
    }

    public String getStrID() {
        return "Program ID " + id.toString();
    }

    public Boolean isNotCompleted() {
        return !stk.isEmpty();
    }

    int getNewId() {
        return ++idCounter;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTable,
                    MyIList<Value> out, MyIDictionary<StringValue, BufferedReader> fileTable,
                    MyIHeap<Integer, Value> heap, IStmt originalProgram) {
        this.stk = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram.deepCopy();
        this.id = getNewId();
        stk.push(originalProgram);
    }

    public PrgState oneStep() throws MyException {
        if (stk.isEmpty()) {
            throw new MyException("Stack is empty!");
        }

        IStmt currentStatement = stk.pop();
        return currentStatement.execute(this);
    }

    public String toString() {
        // we want to print the contents of the stack, the symbol table and the output list
        return "Program ID: " + id.toString() + "\n" +
                "Stack: " + stk.toString() + "\n" +
                "Symbol Table: " + symTable.toString() + "\n" +
                "Output List: " + out.toString() + "\n" +
                "File Table: " + fileTable.toString() + "\n" +
                "Heap: " + heap.toString() + "\n";
    }

    public String toFile() {
        // ANSI escape codes for colors and text styles
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BOLD = "\u001B[1m";

        return ANSI_BOLD + ANSI_RED + "Program ID: " + ANSI_RESET + id.toString() + "\n" +
                ANSI_BOLD + ANSI_RED + "ExeStack: " + ANSI_RESET + stk.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "SymTable: " + ANSI_RESET + symTable.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "OutList: " + ANSI_RESET + out.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "FileTable: " + ANSI_RESET + fileTable.toFile() + "\n" +
                ANSI_BOLD + ANSI_RED + "Heap: " + ANSI_RESET + heap.toFile() + "\n";
    }

    public PrgState deepCopy() {
        return new PrgState(new MyStack<>(),
                new MyDictionary<>(), new MyList<>(),
                new FileTable<>(), new Heap(),
                originalProgram);
    }
}
