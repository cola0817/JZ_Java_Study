package edu.jzxy.cbq.StreamDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;


class StreamBasicUseTest {
    /**
     * Stream 可以看作是一种对集合中元素进行一系列操作的工具，这些操作可以是过滤、映射、排序等。
     * 通过使用 Stream，你可以更简洁、更易读地处理集合数据。
     */
    Stream<String> stream;

    @BeforeEach
    void init(){
        stream = Stream.of("apple", "banner", "orange");
    }

    /**
     * 使用 stream 进行过滤
     */
    @Test
    void filterTest(){
        stream.filter(s -> s.startsWith("a")).forEach(System.out::println);
    }

    /**
     * 使用 stream 进行 map 映射
     */
    @Test
    void mapTest(){
        stream.map(String::toUpperCase).forEach(System.out::println);
    }
    /**
     * 使用 stream 进行排序
     */
    @Test
    void sortTest() {
        stream.sorted().forEach(System.out::println);
    }

    /**
     * 使用 stream 将流转换为其他类型
     */
    @Test
    void collectTest() {
        List<String> stringList = stream.toList();
        stringList.forEach(System.out::println);
    }
}