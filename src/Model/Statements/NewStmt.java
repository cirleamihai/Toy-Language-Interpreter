package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Value.RefValue;
import Model.Type.RefType;
import Model.Value.Value;

public class NewStmt implements IStmt{
    String varName;
    Exp exp;

    public NewStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value var_value = symTable.get(varName);  // we get the variable name
        Value exp_value = exp.eval(symTable, heap);  // we get the value of the expression

        if (var_value == null)
            throw new MyException("The variable is not defined");

        if (!(var_value instanceof RefValue))
            throw new MyException("The variable is not a reference");

        RefType var_type = (RefType) var_value.getType();
        if (exp_value.getType().equals(var_type.getInner())) {
            int address = heap.defaultPut(exp_value);

            //  Setting the address on the heap for the RefValue
            ((RefValue)var_value).setAddress(address);

            return null;
        } else throw new MyException("The types are not equal");
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
}
