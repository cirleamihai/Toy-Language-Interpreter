package View;

import Model.ADT.MyDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Expression.*;
import Model.Statements.*;
import Model.Type.*;
import Model.Value.*;
import Model.PrgState;
import Repository.Repo;
import Controller.Controller;

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


        MyStack<IStmt> mystack = new MyStack<>();
        MyDictionary<String, Value> mydictionary = new MyDictionary<>();
        MyList<Value> mylist = new MyList<>();
        PrgState program = new PrgState(mystack, mydictionary, mylist, ex3);
        PrgState[] prg = new PrgState[1];
        prg[0] = program;

        Repo repository = new Repo(prg);
        Controller controller = new Controller(repository);
        controller.allSteps();
    }
}
