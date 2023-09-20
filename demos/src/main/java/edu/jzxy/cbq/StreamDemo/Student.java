package edu.jzxy.cbq.StreamDemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cola0817
 * @name Student
 * @date 2023/9/20 15:51
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private Integer age;
    private Integer month;
}
