package Repository;

import Exceptions.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Repo implements IRepo {
    PrgState[] program;
    PrintWriter logFile;
    String logFilePath;

    public Repo(PrgState[] program, String logFilePath) throws MyException {
        this.program = program;
        this.logFilePath = logFilePath;

        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (Exception e) { // catching any IO exceptions or other types of exceptions
            throw new MyException(e.getMessage());
        }
    }

    public PrgState getCrtPrg() {
        return this.program[0]; // if we do no multi-threading, getCrtPrg() will
        // always return the first program
    }

    public void logPrgStateExec() throws MyException {
        try {
            String file_string = program[0].toFile();
            logFile.println(file_string);
            logFile.flush();
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }
}
