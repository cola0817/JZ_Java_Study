package edu.jzxy.cbq.IODemo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cola0817
 * @name SinByThread
 * @date 2023/9/20 22:22
 * @since 1.0.0
 */
public class SinByThread {
    private static final int NUM_THREADS = 12;
    private static final File FILE = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\StreamDemo\\demo.csv");

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        List<Double> results = new ArrayList<>();
        double sum = 0;
        long start = System.currentTimeMillis();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(FILE))) {
            for (int i = 0; i < NUM_THREADS; i++) {
                Thread thread = new CalculationThread(inputStream, results);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            for (double result : results) {
                sum += result;
            }

            System.out.println("Sum of Sin Values: " + sum);
            long end = System.currentTimeMillis();
            System.out.println("time: " + (end - start) / 1000 + "s");
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while processing data");
            System.out.printf(e.getMessage());
        }
    }

    static class CalculationThread extends Thread {
        private final DataInputStream inputStream;
        private final List<Double> results;

        CalculationThread(DataInputStream inputStream, List<Double> results) {
            this.inputStream = inputStream;
            this.results = results;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int id;
                    synchronized (inputStream) {
                        if (inputStream.available() <= 0) {
                            break;
                        }
                        id = inputStream.readInt();
                    }

                    double sinValue = Math.sin(id);
                    synchronized (results) {
                        results.add(sinValue);
                    }
//                    System.out.println("ID: " + id + ", Sin Value: " + sinValue);
                }
            } catch (IOException e) {
                System.out.println("An error occurred while processing data");
                e.printStackTrace();
            }
        }
    }
}
