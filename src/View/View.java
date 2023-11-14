package View;

import Exceptions.MyException;
import Model.ADT.*;
import Model.Expression.*;
import Model.Statements.*;
import Model.Type.*;
import Model.Value.*;
import Model.PrgState;
import Repository.Repo;
import Controller.Controller;

import java.io.BufferedReader;
import java.nio.Buffer;

public class View {
    public static void main(String[] args) {
//        int v; v=2; Print(v) is represented as:
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

//        int a; int b; a=2+3*5; b=a+1; Print(b) is represented as:
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

//        bool a; int v; a=true; If (a Then v=2 Else v=3); Print(v) is represented as
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

//        string varf; varf="test.in"; openRFile(varf); int varc; ReadFile(varf, varc);
//        print(varc); readFile(varf, varc); print(varc); closeRFile(varf)
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(".\\Files\\test.in"))),
                        new CompStmt(new openRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                                new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                        new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                                new closeRFile(new VarExp("varf"))))))))));

        MyStack<IStmt> mystack = new MyStack<>();
        MyDictionary<String, Value> mydictionary = new MyDictionary<>();
        MyList<Value> mylist = new MyList<>();
        FileTable<StringValue, BufferedReader> myFileTable = new FileTable<>();
        PrgState program = new PrgState(mystack, mydictionary, mylist, ex4, myFileTable);
        PrgState[] prg = new PrgState[1];
        prg[0] = program;

        String logFilePath = ".\\Files\\log.txt";
        try {
            Repo repository = new Repo(prg, logFilePath);
            Controller controller = new Controller(repository);
            controller.allSteps();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
