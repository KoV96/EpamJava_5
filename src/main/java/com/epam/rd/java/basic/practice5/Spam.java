package com.epam.rd.java.basic.practice5;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Spam {
    private final Thread[] threads;

    public Spam(final String[] messages, final int[] delays) {
        if (messages.length != delays.length) {
            throw new IllegalArgumentException("Invalid input data");
        } else {
            threads = new Thread[messages.length];
            for (int i = 0; i < messages.length; i++) {
                threads[i] = new Worker(messages[i], delays[i]);
            }
        }

    }

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void stop() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(final String[] args) {
        String[] messages = new String[]{"@@@", "bbbbbbb"};
        int[] times = new int[]{333, 222};
        Spam spam = new Spam(messages, times);
        spam.start();
        while (true) {
            try {
                if (System.in.read() == KeyEvent.VK_ENTER) {
                    spam.stop();
                    break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private static class Worker extends Thread {
        private final String message;
        private final int time;

        public Worker(String message, int time) {
            this.message = message;
            this.time = time;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(message);
                    TimeUnit.MILLISECONDS.sleep(time);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

}
