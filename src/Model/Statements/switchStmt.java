package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.Expression.LogicExp;
import Model.Expression.RelationExp;
import Model.PrgState;
import Model.Type.Type;

public class switchStmt implements IStmt {
    Exp exp, exp1, exp2;
    IStmt stmt1, stmt2, stmt3;

    public switchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    public IStmt deepCopy() {
        return new switchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(),
                exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Exp expAndExp1 = new RelationExp(exp, exp, "==");
        Exp expAndExp2 = new RelationExp(exp, exp2, "==");
        IStmt newStmt = new IfStmt(expAndExp1, stmt1,
                new IfStmt(expAndExp2, stmt2, stmt3));

        MyIStack<IStmt> stk = state.getStk();
        stk.push(newStmt);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typecheck(typeEnv);
        Type typExp1 = exp1.typecheck(typeEnv);
        Type typExp2 = exp2.typecheck(typeEnv);

        if (!typExp.equals(typExp1) || !typExp.equals(typExp2)) {
            throw new MyException("The condition of SWITCH does not have the same type as the expressions");
        }

        stmt1.typecheck(typeEnv.deepCopy());
        stmt2.typecheck(typeEnv.deepCopy());
        stmt3.typecheck(typeEnv.deepCopy());

        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch (" + exp.toString() + ") (case (" + exp1.toString() + "): (" + stmt1.toString() + ")) (case (" +
                exp2.toString() + "): (" + stmt2.toString() + ")) (default: (" + stmt3.toString() + "))";
    }
}
