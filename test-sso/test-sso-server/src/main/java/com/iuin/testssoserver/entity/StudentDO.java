package com.iuin.testssoserver.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 学生表
 *
 * @author fa
 * @version 2.0.0
 * @date 2020/8/14 19:37
 */
@Data
@Entity
@Table(schema = "public", name = "student")
public class StudentDO {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(sequenceName = "student_seq", name = "student_seq", allocationSize = 1)
    private Long id;

    /**
     * 年龄
     */
    @Column(columnDefinition = "int8")
    private Integer age;

    /**
     * 性别
     */
    @Column(columnDefinition = "int8")
    private Integer sex;

    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(100)")
    private String name;

    /**
     * 地址
     */
    @Column(columnDefinition = "varchar")
    private String address;

}
