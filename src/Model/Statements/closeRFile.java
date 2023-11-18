package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class closeRFile implements IStmt {
    Exp exp;

    public closeRFile(Exp e) {
        exp = e;
    }

    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new closeRFile(exp.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        StringValue exp_value = (StringValue) exp.eval(symTable, heap);
        if (exp_value.getType().equals(new StringType())) {
            if (fileTable.contains(exp_value)) {
                BufferedReader file_descriptor = fileTable.get(exp_value);
                try {
                    file_descriptor.close();
                    fileTable.remove(exp_value);

                    return state;
                } catch (Exception e) {
                    throw new MyException("Error while closing the file");
                }
            } else throw new MyException("The file is not opened");
        } else throw new MyException("The expression is not a string");
    }
}
