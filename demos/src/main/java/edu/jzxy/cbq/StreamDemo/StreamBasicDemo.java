package edu.jzxy.cbq.StreamDemo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cola0817
 * @name StreamBasicDemo
 * @date 2023/9/20 15:53
 * @since 1.0.0
 */
public class StreamBasicDemo {


    /**
     * 根据班级人数随机生成班级
     *
     * @param classCounts 班级人数
     * @return 生成的班级
     */
    public static Map<String, List<Student>> randomClass(int[] classCounts) {
        Random random = new Random();
        String classNamePrefix = "class_";
        int classIndex = 1;
        Map<String, List<Student>> classMap = new HashMap<>();

        for (int count : classCounts) {
            List<Student> students = new ArrayList<>();
            String className = classNamePrefix + classIndex;
            while (count != 0) {
                Student student = new Student(
                        "student_" + count + "_" + classIndex,
                        random.nextInt(16, 25),
                        random.nextInt(1, 13)
                );
                students.add(student);
                count--;
            }
            classMap.put(className, students);
            classIndex++;
        }
        return classMap;
    }

    /**
     * 打印班级信息
     *
     * @param classMap classMap
     */
    public static void printClassInfo(Map<?, List<Student>> classMap) {
        classMap.forEach((s, students) -> {
            System.out.println(s);
            students.forEach(System.out::println);
        });
    }

    /**
     * 根据月份进行排序
     *
     * @return 排序后的 classMap
     */
    public static Map<Integer, List<Student>> groupedByMonth(Map<String, List<Student>> classMap) {
        Map<Integer, List<Student>> groutedClassMap = new HashMap<>();

        groutedClassMap = classMap
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Student::getMonth));

        return groutedClassMap;
    }


}
