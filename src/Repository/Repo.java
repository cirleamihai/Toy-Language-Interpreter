package Repository;

import Model.PrgState;

public class Repo implements IRepo {
    PrgState[] program;

    public Repo(PrgState[] program) {
        this.program = program;
    }

    public PrgState getCrtPrg() {
        return this.program[0]; // if we do no multi-threading, getCrtPrg() will
                                // always return the first program
    }
}
