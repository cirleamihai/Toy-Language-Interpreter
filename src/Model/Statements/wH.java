package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class wH implements IStmt { // Heap Writing
    String varName;
    Exp exp;

    public wH(String varName, Exp exp) {
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

        // we check if the key of the expression exists in the heap
        RefValue ref_value = (RefValue) var_value;
        int address = ref_value.getAddress();
        if (!heap.contains(address))
            throw new MyException("The address " + varName + " is pointing to is not in the heap");

        // we check if the result from the heap has the same type as the expression
        Value heap_value = heap.get(address);
        if (!heap_value.getType().equals(exp_value.getType()))
            throw new MyException("The types for" + varName + "and " + exp.toString() + " are not equal");

        // we update the heap value
        heap.update(address, exp_value);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(varName);
        Type typeExp = exp.typecheck(typeEnv);

        if (!typeVar.equals(new RefType(typeExp)))
            throw new MyException("Writing on Heap stmt: right hand side and left hand side have different types!");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new wH(varName, exp.deepCopy());
    }
}
