package Controller;

import Model.ADT.MyIList;
import Model.Value.Value;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Repository.IRepo;
import Exceptions.*;

public class Controller {
    IRepo repo;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStk();

        if (stack.isEmpty()) {
            throw new MyException("Stack is empty!");
        }

        IStmt currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allSteps() {
        PrgState prg = repo.getCrtPrg();
        try {
            while (!prg.getStk().isEmpty()) {
                prg = oneStep(prg);

                System.out.println(prg.toString());
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        MyIList<Value> out_list = prg.getOut();
        for (int i = 0; i < prg.getOut().len(); ++i){
            System.out.println(out_list.get(i).toString());
        }
    }
}
