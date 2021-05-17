package com.epam.rd.java.basic.practice5;

public class Part1 {

    public static void main(String[] args) {
        Thread first = new FirstThread();
        Thread second = new Thread(new RunnableThread());
        first.start();
        try {
            first.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        second.start();
        try {
            second.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class RunnableThread implements Runnable {
        //new
        @Override
        public void run() {
            for (int i = 4; i > 0; i--) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static class FirstThread extends Thread {

        @Override
        public void run() {
            for (int i = 4; i > 0; i--) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
