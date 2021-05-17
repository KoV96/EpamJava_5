package com.epam.rd.java.basic.practice5;

public class Part3 {
    final Object monitor = new Object();

    int numberOfIteration;

    private final Thread[] threads;

    private int counter;

    private int counter2;

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void joinAll() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Part3(int numberOfThreads, int numberOfIteration){
        threads = new Thread[numberOfThreads];
        this.numberOfIteration = numberOfIteration;
        this.counter = 0;
        this.counter2 = 0;
    }

    public static void main(final String[] args) {
        //empty
    }

    public void compare() {
        for (int i = 0; i < threads.length; i++){
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIteration; j++){
                    output();
                }
            });
        }
        start();
        joinAll();
    }

    private void output() {
        System.out.println(counter == counter2);
        counter++;
        try {
            Thread.sleep(100);
            counter2++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void compareSync() {
        for (int i = 0; i < threads.length; i++){
            threads[i] = new Thread(() -> {
                synchronized (monitor) {
                    for (int j = 0; j < numberOfIteration; j++) {
                        output();
                    }
                }
            });
        }
        start();
        joinAll();
    }

}
