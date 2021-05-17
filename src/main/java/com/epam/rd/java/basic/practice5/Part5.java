package com.epam.rd.java.basic.practice5;

import java.io.*;
import java.nio.file.Files;

public class Part5 {//new
    private static final int COLUMNS = 20;
    private static final int ROWS = 10;
    private static final String EOL = System.lineSeparator();
    private static final String FILE_NAME = "part5.txt";
    private static final String ENCODING = "Cp1251";
    private final RandomAccessFile outer;
    private int pointer;
    private final Thread[] threads = new Thread[ROWS];

    public Part5(RandomAccessFile outer) {
        this.outer = outer;
        createThreads();
        try {
            start();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public synchronized int getPointer() {
        return pointer;
    }

    public synchronized RandomAccessFile getOuter() {
        return outer;
    }

    public void start() throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }
    }

    private void createThreads() {
        for (int i = 0; i < threads.length; i++) {
            int num = i;
            threads[i] = new Thread(() -> {
                try {
                    writeLine(num);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    synchronized void writeLine(int num) {
        synchronized (outer) {
            try {
                String line = getLine(num);
                getOuter().seek(pointer);
                getOuter().write(line.getBytes(ENCODING));
                setPointer(getPointer() + line.length());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private String getLine(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < COLUMNS; i++) {
            sb.append(num);
            if (i == COLUMNS - 1) {
                sb.append(EOL);
            }
        }
        return sb.toString();
    }


    public static void main(final String[] args) {
        try {
            Files.deleteIfExists(new File(FILE_NAME).toPath());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File(FILE_NAME), "rw");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        new Part5(raf);
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            while (true){
                String line = br.readLine();
                if (line == null) break;
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
