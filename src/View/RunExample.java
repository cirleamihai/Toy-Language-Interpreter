package View;

import Controller.Controller;
import Exceptions.MyException;

public class RunExample extends Command {
    private final Controller ctr;
    public RunExample(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try {
            ctr.allSteps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
