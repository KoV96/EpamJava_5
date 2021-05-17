package com.epam.rd.java.basic.practice5;

import java.io.*;

public class Part4 {
    private static final String EOL = System.lineSeparator();
    private static final String FILE_NAME = "part4.txt";
    private final Thread[] threads;
    private volatile int max;
    private final int row;
    private final int column;

    public Part4(int row, int column) {
        if (row == 0 || column == 0) {
            throw new IllegalArgumentException("Value of matrix size must be greater than 0");
        }
        this.row = row;
        this.column = column;
        threads = new Thread[this.row];
    }

    private void findMaxWithoutThreads() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < row; i++) {
            int maxInRow = getMax(i);
            if (maxInRow > max) max = maxInRow;
        }
        System.out.println(max + EOL +
                (System.currentTimeMillis() - start));
    }

    private int getMax(int i) {
        int max2 = 0;
        int[] numMatrix = new int[column];
        String line = getLine(i);
        if (line == null) throw new NullPointerException("Empty line in file matrix");
        String[] lineByNum = line.split(" ");
        for (int j = 0; j < column; j++) {
            numMatrix[j] = Integer.parseInt(lineByNum[j]);
            if (numMatrix[j] > max2) max2 = numMatrix[j];
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return max2;
    }

    private String getLine(int lineNumber) {
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            for (int i = 0; i <= lineNumber; i++) {
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return line;
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

    private void findMaxWithThreads() {
        reverse();
        createThreads();
        start();
        long start = System.currentTimeMillis();
        joinAll();
        System.out.println(max + EOL +
                (System.currentTimeMillis() - start));
    }

    private void createThreads() {
        for (int i = 0; i < row; i++) {
            final int lineNumber = i;
            threads[i] = new Thread(() -> {
                try {
                    int maxInLine = getMax(lineNumber);
                    if (maxInLine > max) max = maxInLine;
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }


    private void reverse() {
        this.max = 0;
    }

    public static void main(final String[] args) {
        Part4 part4 = new Part4(4, 100);
        part4.findMaxWithThreads();
        part4.findMaxWithoutThreads();
    }

}
