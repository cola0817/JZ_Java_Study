package edu.jzxy.cbq.IODemo.V2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Cola0817
 * @name ReadV6
 * @date 2023/9/20 23:13
 * @since 1.0.0
 */
public class ReadV6 {
    private static final int NUM_THREADS = 4; // 并发线程数
    private static final File FILE = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\StreamDemo\\demo.csv");


    public static void main(String[] args) {
//        generateData(); // 生成数据
        calculateSum(); // 计算累加的sin和
        calculateFastestTime(); // 计算最快时间并显示值和计算时间
    }

    // 生成1到1000万数据，保存到data.txt文件
    private static void generateData() {
        try (RandomAccessFile file = new RandomAccessFile(FILE, "rw")) {
            for (int i = 1; i <= 10000000; i++) {
                file.writeInt(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取数据，计算累加的sin和
    private static void calculateSum() {
        double sum = 0;
        try (RandomAccessFile file = new RandomAccessFile(FILE, "r")) {
            int num;
            while (file.getFilePointer() < file.length()) {
                num = file.readInt();
                sum += Math.sin(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("累加的sin和：" + sum);
    }

    // 使用并发分块处理，计算最快时间并显示值和计算时间
    private static void calculateFastestTime() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Double>> results = new ArrayList<>();

        try (RandomAccessFile file = new RandomAccessFile(FILE, "r")) {
            long chunkSize = file.length() / NUM_THREADS;

            for (int i = 0; i < NUM_THREADS; i++) {
                long start = i * chunkSize;
                long end = (i == NUM_THREADS - 1) ? file.length() : (i + 1) * chunkSize;
                results.add(executorService.submit(new CalculationTask(file, start, end)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double fastestSum = Double.NEGATIVE_INFINITY;
        long startTime = System.currentTimeMillis();

        for (Future<Double> result : results) {
            try {
                double sum = result.get();
                if (sum > fastestSum) {
                    fastestSum = sum;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("最快计算的sin和：" + fastestSum);
        System.out.println("计算时间：" + (endTime - startTime) + "毫秒");

        executorService.shutdown();
    }

    // 并发计算任务
    private static class CalculationTask implements Callable<Double> {
        private final RandomAccessFile file;
        private final long start;
        private final long end;

        public CalculationTask(RandomAccessFile file, long start, long end) {
            this.file = file;
            this.start = start;
            this.end = end;
        }

        @Override
        public Double call() throws IOException {
            double sum = 0;
            file.seek(start);
            while (file.getFilePointer() < end) {
                int num = file.readInt();
                sum += Math.sin(num);
            }
            return sum;
        }
    }
}
