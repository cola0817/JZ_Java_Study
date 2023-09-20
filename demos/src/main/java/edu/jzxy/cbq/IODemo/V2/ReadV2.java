package edu.jzxy.cbq.IODemo.V2;

import java.io.*;

/**
 * @author Cola0817
 * @name ReadV2
 * @date 2023/9/20 22:46
 * @since 1.0.0
 */
public class ReadV2 {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\IODemo\\demoV2.txt");
        long start = System.currentTimeMillis();
        try (DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))){
            int bufferSize = 8192; // 设置缓冲区大小，可以根据实际情况调整
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i += 4) {
                    int id = ((buffer[i] & 0xFF) << 24) | ((buffer[i+1] & 0xFF) << 16) | ((buffer[i+2] & 0xFF) << 8) | (buffer[i+3] & 0xFF);
                    System.out.println(id);
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("time: " + (end - start) / 1000 + "s");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
