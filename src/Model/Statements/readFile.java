package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class readFile implements IStmt {
    Exp exp;
    String varName;

    public readFile(Exp e, String v) {
        exp = e;
        varName = v;
    }

    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new readFile(exp.deepCopy(), varName);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        StringValue exp_value = (StringValue) exp.eval(symTbl, heap);
        if (symTbl.contains(varName)) {
            Value var_value = symTbl.get(varName);
            if (var_value.getType().equals(new IntType())) {
                if (exp_value.getType().equals(new StringType())) {
                    String filename = exp_value.getVal();

                    BufferedReader file_descriptor = fileTable.get(exp_value); // we get the file descriptor from the file table
                    if (file_descriptor != null) {
                        try {
                            String line = file_descriptor.readLine();
                            if (line == null) {
                                symTbl.put(varName, new IntType().defaultValue());
                            } else {
                                int number = Integer.parseInt(line);
                                symTbl.put(varName, new IntValue(number));  // we add the value to the symbol table
                            }
                        } catch (Exception e) {
                            throw new MyException("Error while reading from file " + filename);
                        }

                        return state;
                    } else throw new MyException("File " + filename + " is not opened");

                } else throw new MyException("Expression " + exp.toString() + " is not a string");
            } else throw new MyException("Variable Type is not Integer");
        } else throw new MyException("This variable is not declared");
    }
}
