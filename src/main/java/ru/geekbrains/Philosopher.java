package ru.geekbrains;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private static final int MAX_COUNT_OF_MEALS = 5;
    private int numberOfMeals = 0;
    private int forksCount = 0;
    private final Table table;

    public Philosopher(Table table) {
        this.table = table;
    }

    @Override
    public void run() {

        doAction(String.format("%s: thinking now", Thread.currentThread().getName()));

        while (numberOfMeals <= MAX_COUNT_OF_MEALS){

            if (forksCount == 0 && table.tryGetForks()){
                forksCount = 2;
                numberOfMeals++;
                doAction(String.format("%s: it is my %d meal and I`am eating\n", Thread.currentThread().getName(), numberOfMeals));
                table.putForksOnTable();
                forksCount = 0;
            }else {
                doAction(String.format("%s: thinking now", Thread.currentThread().getName()));
            }
        }

        System.out.printf("%s: is full!!!\n", Thread.currentThread().getName());
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
