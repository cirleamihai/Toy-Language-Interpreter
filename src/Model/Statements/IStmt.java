package Model.Statements;

import Exceptions.MyException;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
}