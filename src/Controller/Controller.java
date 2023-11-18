package Controller;

import Model.ADT.MyIList;
import Model.Value.RefValue;
import Model.Value.Value;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Repository.IRepo;
import Exceptions.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            repo.logPrgStateExec();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        try {
            while (!prg.getStk().isEmpty()) {
                prg = oneStep(prg);

                System.out.println(prg.toString());
                repo.logPrgStateExec();

                // TODO - add the garbage collector
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        MyIList<Value> out_list = prg.getOut();
        for (int i = 0; i < prg.getOut().len(); ++i) {
            System.out.println(out_list.get(i).toString());
        }
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(e -> e instanceof RefValue)
                .map(e -> {
                    RefValue v = (RefValue) e;
                    return v.getAddress();
                }).collect(Collectors.toList()); // TODO -> read the Lecture 5 to understand Functional Programming
    })
//    }
    }
