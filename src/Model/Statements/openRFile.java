package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.ADT.MyIDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStmt {
    Exp exp;

    public openRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(exp.deepCopy());
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        StringValue exp_value = (StringValue) exp.eval(symTbl, heap);
        if (exp_value.getType().equals(new StringType())) {
            if (!fileTable.contains(exp_value)) {
                // going to open the file and store the file descriptor in the file table
                try {
                    BufferedReader fileDescriptor = new BufferedReader(new FileReader(exp_value.getVal()));
                    fileTable.put(exp_value, fileDescriptor);

                    return state;
                } catch (IOException e) {
                    throw new MyException("Error while reading from file");
                } catch (Exception e) {
                    throw new MyException("The file could not be opened");
                }
            } else throw new MyException("The file is already opened");
        } else throw new MyException("The expression is not a string");

    }
}
