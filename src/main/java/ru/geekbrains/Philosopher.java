package ru.geekbrains;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private static final int MAX_COUNT_OF_MEALS = 5;
    private boolean hasLeftFork;
    private boolean hasRightFork;
    private int numberOfMeals = 0;
    private final Table table;

    public Philosopher(Table table) {
        this.table = table;
    }

    @Override
    public void run() {

        doAction(String.format("%s: thinking now", Thread.currentThread().getName()));

        while (numberOfMeals < MAX_COUNT_OF_MEALS){

            if (tryGetForks()){
                numberOfMeals++;
                doAction(String.format("%s: it is my %d meal and I`am eating", Thread.currentThread().getName(), numberOfMeals));
                putForks();
                System.out.printf("%s: put forks on table\n", Thread.currentThread().getName());
            }else {
                doAction(String.format("%s: thinking now", Thread.currentThread().getName()));
            }
        }

        System.out.printf("%s: is full!!!\n", Thread.currentThread().getName());
    }

    private boolean tryGetForks(){
        if (!hasLeftFork){
            hasLeftFork = table.tryGetForks();
        }

        if (!hasRightFork){
            hasRightFork = table.tryGetForks();
        }

        return hasRightFork && hasLeftFork;
    }

    private void putForks(){
        if (hasLeftFork){
            hasLeftFork = false;
            table.putForksOnTable();
        }

        if (hasRightFork){
            hasRightFork = false;
            table.putForksOnTable();
        }
    }

    private void doAction(String message){
        System.out.println(message);

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
