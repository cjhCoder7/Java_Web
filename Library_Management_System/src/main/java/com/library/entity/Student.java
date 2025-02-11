package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private int sid;
    private String name;
    private int age;
    private String gender;
}
