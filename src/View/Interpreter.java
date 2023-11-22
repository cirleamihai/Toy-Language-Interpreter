package View;

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

class Interpreter {

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

//        int a; int b; a=2+3*5; b=a+1; Print(b) is represented as:
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
        PrgState prg1 = new PrgState(stack1, dict1, list1, fileTable1, heap1, ex1);
        PrgState[] program1 = new PrgState[1];
        program1[0] = prg1;

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
        PrgState prg2 = new PrgState(stack2, dict2, list2, fileTable2, heap2, ex2);
        PrgState[] program2 = new PrgState[1];
        program2[0] = prg2;

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
        PrgState prg3 = new PrgState(stack3, dict3, list3, fileTable3, heap3, ex3);
        PrgState[] program3 = new PrgState[1];
        program3[0] = prg3;


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
        PrgState prg4 = new PrgState(stack4, dict4, list4, fileTable4, heap4, ex4);
        PrgState[] program4 = new PrgState[1];
        program4[0] = prg4;

        IRepo repo4 = null;
        try {
            repo4 = new Repo(program4, ".\\Files\\log4.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Controller ctrl4 = new Controller(repo4);

        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.show();
    }
}
