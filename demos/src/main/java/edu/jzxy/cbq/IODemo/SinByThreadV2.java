package edu.jzxy.cbq.IODemo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Cola0817
 * @name SinByThreadV2
 * @date 2023/9/20 22:35
 * @since 1.0.0
 */
public class SinByThreadV2 {
    private static final int NUM_THREADS = 10;
    private static final File FILE = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\StreamDemo\\demo.csv");

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<CalculationTask> tasks = new ArrayList<>();
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        long start = System.currentTimeMillis();


        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(FILE))) {
            for (int i = 0; i < NUM_THREADS; i++) {
                CalculationTask task = new CalculationTask(inputStream, sum);
                tasks.add(task);
                executorService.execute(task);
            }

            executorService.shutdown();
            while (!executorService.isTerminated()) {
                // 等待所有任务完成
            }

            System.out.println("Sum of Sin Values: " + sum.get());
            long end = System.currentTimeMillis();
            System.out.println("time: " + (end - start) / 1000 + "s");
        } catch (IOException e) {
            System.out.println("An error occurred while processing data");
            e.printStackTrace();
        }
    }

    static class CalculationTask implements Runnable {
        private final DataInputStream inputStream;
        private final AtomicReference<Double> sum;

        CalculationTask(DataInputStream inputStream, AtomicReference<Double> sum) {
            this.inputStream = inputStream;
            this.sum = sum;
        }

        @Override
        public void run() {
            try {
                List<Double> results = new ArrayList<>();
                while (true) {
                    int id;
                    synchronized (inputStream) {
                        if (inputStream.available() <= 0) {
                            break;
                        }
                        id = inputStream.readInt();
                    }

                    double sinValue = Math.sin(id);
                    results.add(sinValue);
                }

                double localSum = 0;
                for (double result : results) {
                    localSum += result;
                }

                while (true) {
                    Double currentSum = sum.get();
                    Double updatedSum = currentSum + localSum;
                    if (sum.compareAndSet(currentSum, updatedSum)) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while processing data");
                e.printStackTrace();
            }
        }
    }
}
