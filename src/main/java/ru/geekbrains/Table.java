package ru.geekbrains;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int forksOnTable = 5;
    private static final int PHILOSOPHERS_COUNT = 5;
    private final List<Philosopher> philosophers = new ArrayList<>();

    public synchronized boolean tryGetForks () {
        if (forksOnTable < 2) {
            return false;
        }
        forksOnTable -= 2;
        return true;
    }

    public synchronized void putForksOnTable () {
        forksOnTable += 2;
    }

    public void start() {

        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            Philosopher philosopher = new Philosopher(this);
            philosophers.add(philosopher);
            Thread philosopherThread = new Thread(philosopher, "philosopher " + (i + 1));
            philosopherThread.start();
        }
    }
}

