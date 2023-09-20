package edu.jzxy.cbq.StreamDemo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Cola0817
 * @name StreamStudy
 * @date 2023/9/20 21:57
 * @since 1.0.0
 */
public class StreamStudy {
    public static void main(String[] args) {

        List<Integer> ids = IntStream
                .rangeClosed(1,1000_0000)
                .boxed()
                .toList();

        File file = new File("C:\\Users\\86132\\IdeaProjects\\JZ_Java_Study\\demos\\src\\main\\java\\edu\\jzxy\\cbq\\StreamDemo\\demo.csv");

        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file))){
            for (Integer id : ids) {
                output.writeInt(id);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
