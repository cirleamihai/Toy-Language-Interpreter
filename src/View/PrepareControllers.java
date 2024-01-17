package View;

import Exceptions.MyException;
import Model.ADT.*;
import Model.Expression.*;
import Model.Statements.*;
import Model.Type.*;
import Model.Value.*;
import Model.PrgState;
import Repository.IRepo;
import Repository.Repo;
import Controller.Controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class PrepareControllers {
    List<Controller> controllers;
    List<IStmt> programStatements;

    public PrepareControllers() throws MyException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        // Creating the program state with stack, SymTbl, etc
        MyStack<IStmt> stack1 = new MyStack<>();
        MyDictionary<String, Value> dict1 = new MyDictionary<>();
        MyList<Value> list1 = new MyList<>();
        FileTable<StringValue, BufferedReader> fileTable1 = new FileTable<>();
        Heap heap1 = new Heap();
        ex1.typecheck(new MyDictionary<>());
        PrgState prg1 = new PrgState(stack1, dict1, list1, fileTable1, heap1, ex1);
        List<PrgState> program1 = new ArrayList<>();
        program1.add(prg1);

        IRepo repo1 = null;
        try {
            repo1 = new Repo(program1, ".\\Files\\log1.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Controller ctrl1 = new Controller(repo1);

//        bool a; int v; a=true; If (a Then v=2 Else v=3); Print(v) is represented as
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new RelationExp(new VarExp("v"), new ValueExp(new IntValue(0)), "=="), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        // Creating the program state with stack, SymTbl, etc
        MyStack<IStmt> stack2 = new MyStack<>();
        MyDictionary<String, Value> dict2 = new MyDictionary<>();
        MyList<Value> list2 = new MyList<>();
        FileTable<StringValue, BufferedReader> fileTable2 = new FileTable<>();
        Heap heap2 = new Heap();
        ex2.typecheck(new MyDictionary<>());
        PrgState prg2 = new PrgState(stack2, dict2, list2, fileTable2, heap2, ex2);
        List<PrgState> program2 = new ArrayList<>();
        program2.add(prg2);

        IRepo repo2 = null;
        try {
            repo2 = new Repo(program2, ".\\Files\\log2.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Controller ctrl2 = new Controller(repo2);

//        string varf; varf="test.in"; openRFile(varf); int varc; ReadFile(varf, varc);
//        print(varc); readFile(varf, varc); print(varc); closeRFile(varf)
        IStmt ex3 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(".\\Files\\test.in"))),
                        new CompStmt(new openRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                                new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                        new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                                new closeRFile(new VarExp("varf"))))))))));


        // Creating the program state with stack, SymTbl, etc
        MyStack<IStmt> stack3 = new MyStack<>();
        MyDictionary<String, Value> dict3 = new MyDictionary<>();
        MyList<Value> list3 = new MyList<>();
        FileTable<StringValue, BufferedReader> fileTable3 = new FileTable<>();
        Heap heap3 = new Heap();
        ex3.typecheck(new MyDictionary<>());
        PrgState prg3 = new PrgState(stack3, dict3, list3, fileTable3, heap3, ex3);
        List<PrgState> program3 = new ArrayList<>();
        program3.add(prg3);


        IRepo repo3 = null;
        try {
            repo3 = new Repo(program3, ".\\Files\\log3.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        Controller ctrl3 = new Controller(repo3);

//        Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
        IStmt ex4 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        // Creating the program state with stack, SymTbl, etc
        MyStack<IStmt> stack4 = new MyStack<>();
        MyDictionary<String, Value> dict4 = new MyDictionary<>();
        MyList<Value> list4 = new MyList<>();
        FileTable<StringValue, BufferedReader> fileTable4 = new FileTable<>();
        Heap heap4 = new Heap();
        ex4.typecheck(new MyDictionary<>());
        PrgState prg4 = new PrgState(stack4, dict4, list4, fileTable4, heap4, ex4);
        List<PrgState> program4 = new ArrayList<>();
        program4.add(prg4);

        IRepo repo4 = null;
        try {
            repo4 = new Repo(program4, ".\\Files\\log4.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Controller ctrl4 = new Controller(repo4);

//        int v; Ref int a; v=10; new(a,22);
//        fork(wH(a,30);v=32;print(v);print(rH(a)));
//        print(v);print(rH(a))
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rH(new VarExp("a")))))))));

        // Creating the program state with stack, SymTbl, etc
        MyStack<IStmt> stack5 = new MyStack<>();
        MyDictionary<String, Value> dict5 = new MyDictionary<>();
        MyList<Value> list5 = new MyList<>();
        FileTable<StringValue, BufferedReader> fileTable5 = new FileTable<>();
        Heap heap5 = new Heap();
        ex5.typecheck(new MyDictionary<>());
        PrgState prg5 = new PrgState(stack5, dict5, list5, fileTable5, heap5, ex5);
        List<PrgState> program5 = new ArrayList<>();
        program5.add(prg5);

        IRepo repo5 = null;
        try {
            repo5 = new Repo(program5, ".\\Files\\log5.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Controller ctrl5 = new Controller(repo5);

        controllers = new ArrayList<>();
        programStatements = new ArrayList<>();
        controllers.add(ctrl1);
        programStatements.add(ex1);

        controllers.add(ctrl2);
        programStatements.add(ex2);

        controllers.add(ctrl3);
        programStatements.add(ex3);

        controllers.add(ctrl4);
        programStatements.add(ex4);

        controllers.add(ctrl5);
        programStatements.add(ex5);
    }

    public List<Controller> getControllers() {
        return controllers;
    }

    public List<IStmt> getProgramStatements() {
        return programStatements;
    }
}
