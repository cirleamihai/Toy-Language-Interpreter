package Repository;

import Exceptions.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo {
    List<PrgState> prgList;
    List<PrgState> ogPrgList;
    PrintWriter logFile;
    String logFilePath;

    public Repo(List<PrgState> prgList, String logFilePath) throws MyException {
        this.prgList = prgList;
        this.logFilePath = logFilePath;
        this.ogPrgList = prgList;

        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (Exception e) { // catching any IO exceptions or other types of exceptions
            throw new MyException(e.getMessage());
        }
    }

    public void loadOgPrgList() {
        ogPrgList = new ArrayList<>();
        ogPrgList.add(prgList.get(0).deepCopy());
    }

    public void setOgPrgList() {
        prgList = ogPrgList;
    }

    public void logPrgStateExec(PrgState crtProgram) throws MyException {
        try {
            String file_string = crtProgram.toFile();
            logFile.println(file_string);
            logFile.flush();
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    public List<PrgState> getPrgList() {
        return prgList;
    }

    public void setPrgList(List<PrgState> list) {
        prgList = list;
    }
}
