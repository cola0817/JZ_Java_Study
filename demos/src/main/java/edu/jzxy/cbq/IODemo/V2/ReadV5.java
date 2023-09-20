package edu.jzxy.cbq.IODemo.V2;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Cola0817
 * @name ReadV5
 * @date 2023/9/20 23:08
 * @since 1.0.0
 */
public class ReadV5 {
    private static final int NUM_THREADS = 4; // 并发线程数
    private static final File FILE = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\IODemo\\demoV2.txt");

    public static void main(String[] args) {
//        generateData(); // 生成数据
        calculateSum(); // 计算累加的sin和
        calculateFastestTime(); // 计算最快时间并显示值和计算时间
    }

    // 生成1到1000万数据，保存到data.txt文件
    private static void generateData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (int i = 1; i <= 10000000; i++) {
                writer.write(i + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取数据，计算累加的sin和
    private static void calculateSum() {
        double sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int num = Integer.parseInt(line);
                sum += Math.sin(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("累加的sin和：" + sum);
    }

    // 使用并发，计算最快时间并显示值和计算时间
    private static void calculateFastestTime() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        double fastestSum = Double.NEGATIVE_INFINITY;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.execute(new CalculationTask(i));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("计算时间：" + (endTime - startTime) + "毫秒");
    }

    // 并发计算任务
    private static class CalculationTask implements Runnable {
        private final int threadNumber;

        public CalculationTask(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            double sum = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber % NUM_THREADS == threadNumber) {
                        int num = Integer.parseInt(line);
                        sum += Math.sin(num);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + threadNumber + "计算的sin和：" + sum);
        }
    }
}
