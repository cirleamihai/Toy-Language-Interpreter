package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {
    String id;
    Type type;

    public VarDeclStmt(String i, Type t) {
        id = i;
        type = t;
    }

    public String toString() {
        return type.toString() + " " + id;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();

        if (tbl.contains(id))
            throw new MyException("Variable already declared!");

        tbl.put(id, type.defaultValue());

        return state;
    }

    public IStmt deepCopy() {
        return new VarDeclStmt(id, type);
    }
}
