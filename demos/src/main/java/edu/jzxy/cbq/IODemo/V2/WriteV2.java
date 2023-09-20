package edu.jzxy.cbq.IODemo.V2;

import java.io.*;
import java.util.stream.IntStream;

/**
 * @author Cola0817
 * @name WriteV2
 * @date 2023/9/20 22:47
 * @since 1.0.0
 */
public class WriteV2 {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\IODemo\\demoV2.txt");
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            IntStream.rangeClosed(1, 10000000)
                    .parallel()
                    .forEach(i -> {
                        String data = String.valueOf(i) + System.lineSeparator(); // 将换行符放在字符串中
                        try {
                            bos.write(data.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

