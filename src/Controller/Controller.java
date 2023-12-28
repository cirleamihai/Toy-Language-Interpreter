package Controller;

import Model.ADT.MyIHeap;
import Model.ADT.MyIList;
import Model.Value.RefValue;
import Model.Value.Value;
import Model.PrgState;
import Repository.IRepo;
import Exceptions.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepo repo;
    ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    public void allSteps() {
        executor = Executors.newFixedThreadPool(2);

        // we make a copy of the og program
        repo.loadOgPrgList();

        // remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        MyIList<Value> out = prgList.get(0).getOut();
        while (!prgList.isEmpty()) {

            // garbage collector
            List<Integer> symTableAddr = getAddrFromSymTable(prgList.get(0).getSymTable().getContent().values());
            List<Integer> heapAddr = getAddrFromSymTable(prgList.get(0).getHeap().getContent().values());
            symTableAddr.addAll(heapAddr);

            prgList.forEach(prg -> {
                List<Integer> localSymTableAddr = getAddrFromSymTable(prg.getSymTable().getContent().values());
                symTableAddr.addAll(localSymTableAddr);
            });

            // Making sure that we keep one reference to only one heap
            Map<Integer, Value> newHeap = garbageCollector(symTableAddr, prgList.get(0).getHeap().getContent());
            prgList.forEach(prg -> prg.getHeap().setContent(newHeap));

            // one step for all programs
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        // close the executor
        executor.shutdown();
        if(prgList.isEmpty()) {
            repo.setOgPrgList();
        } else {
            repo.setPrgList(prgList);
        }

        // print the output
       out.getContent().forEach(elem -> System.out.println(elem.toString()));
    }

    void oneStepForAllPrg(List<PrgState> prgList) {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        //Transforming each PrgState from prgList into a Callable object that,
        // when called, will execute the oneStep method on that PrgState.
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            // we can throw only RuntimeException from a lambda
                            System.out.println(e.getMessage());
                        }
                        return null;
                    }).filter(p -> p != null).collect(Collectors.toList());

            // Add the newly created programs to the list of existing programs
            prgList.addAll(newPrgList);
        } catch (InterruptedException | RuntimeException e) {
            System.out.println(e.getMessage());
        }


        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        repo.setPrgList(prgList);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(e -> e instanceof RefValue)
                .map(e -> {
                    RefValue v = (RefValue) e;
                    return v.getAddress();
                }).collect(Collectors.toList());
    }
}