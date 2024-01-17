package Repository;

import Model.PrgState;
import Exceptions.MyException;

import java.util.List;

public interface IRepo {
    void logPrgStateExec(PrgState crtProgram) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    void loadOgPrgList();
    void setOgPrgList();
    PrgState getPrgById(String strId);
}
