package edu.jzxy.cbq.StreamDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class StreamBasicDemoTest {
    Map<String, List<Student>> classMap;

    @BeforeEach
    void init(){
        int[] classCounts = {48, 56, 32};
        classMap = StreamBasicDemo.randomClass(classCounts);
    }

    @Test
    void printInfoTest() {
        StreamBasicDemo.printClassInfo(classMap);
    }

    @Test
    void groupedByMonthTest() {
        System.out.println("排序后的 map");
        StreamBasicDemo.printClassInfo(StreamBasicDemo.groupedByMonth(classMap));
        System.out.println("统计数量");
        List<Student> studentList = StreamBasicDemo.groupedByMonth(classMap)
                .values()
                .stream()
                .flatMap(List::stream)
                .toList();
        System.out.println("studentList 的大小为" +
                " = " + studentList.size());

    }
}