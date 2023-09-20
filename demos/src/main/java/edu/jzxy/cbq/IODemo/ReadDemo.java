package edu.jzxy.cbq.IODemo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Cola0817
 * @name ReadDemo
 * @date 2023/9/20 22:15
 * @since 1.0.0
 */
public class ReadDemo {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\StreamDemo\\demo.csv");
        double sum = 0;
        long start = System.currentTimeMillis();
        try (DataInputStream input = new DataInputStream(new FileInputStream(file))){
            while (input.available() > 0) {
                int id = input.readInt();
                double sinValue = Math.sin(id);
                sum += sinValue;
            }
            System.out.println("Sum of Sin Values: " + sum);
            long end = System.currentTimeMillis();
            System.out.println("time: " + (end - start) / 1000 + "s");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
