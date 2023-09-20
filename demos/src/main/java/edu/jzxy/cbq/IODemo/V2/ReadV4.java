package edu.jzxy.cbq.IODemo.V2;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Cola0817
 * @name ReadV4
 * @date 2023/9/20 23:00
 * @since 1.0.0
 */
public class ReadV4 {
    private static final int BUFFER_SIZE = 8192;
    private static final int THREAD_COUNT = 4; // 可根据实际情况调整线程数量

    public static void main(String[] args) {
        File file = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\IODemo\\demoV2.txt");
        long start = System.currentTimeMillis();

        double sum = 0;

        try (DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                executor.execute(new ProcessData(buffer, bytesRead, sum));
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // 等待所有线程执行完毕
            }

            long end = System.currentTimeMillis();
            System.out.println("Sum: " + sum);
            System.out.println("Time: " + (end - start) / 1000 + "s");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static class ProcessData implements Runnable {
        private byte[] buffer;
        private int bytesRead;
        private double sum;

        public ProcessData(byte[] buffer, int bytesRead, double sum) {
            this.buffer = buffer;
            this.bytesRead = bytesRead;
            this.sum = sum;
        }

        @Override
        public void run() {
            for (int i = 0; i < bytesRead; i += 4) {
                int id = ((buffer[i] & 0xFF) << 24) | ((buffer[i + 1] & 0xFF) << 16) | ((buffer[i + 2] & 0xFF) << 8) | (buffer[i + 3] & 0xFF);
                sum += Math.sin(id);
            }
        }
    }
}
