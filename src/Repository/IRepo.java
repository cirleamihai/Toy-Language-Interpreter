package Repository;

import Model.PrgState;
import Exceptions.MyException;

public interface IRepo {
    PrgState getCrtPrg();
    void logPrgStateExec() throws MyException;
}
