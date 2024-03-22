package ru.geekbrains;

import java.util.*;

public class Table {
    private static final int PHILOSOPHERS_COUNT = 5;
    private List<Fork> forks;

    public Table() {
        generateForks();
    }

    public synchronized boolean tryGetForks () {
        Fork fork = forks.stream().filter(f -> !f.isOccupied()).findFirst().orElse(null);
        if (fork == null){
            return false;
        }

        fork.setOccupied(true);
        return true;
    }

    public synchronized void putForksOnTable () {
        Fork fork = forks.stream().filter(Fork::isOccupied).findFirst().orElse(null);
        if (fork == null) return;
        fork.setOccupied(false);
    }

    public void start() {
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            Thread philosopherThread = new Thread(new Philosopher(this), "philosopher " + (i + 1));
            philosopherThread.start();
        }
    }

    private void generateForks() {
        forks = new ArrayList<>();

        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            forks.add(new Fork());
        }
    }
}

