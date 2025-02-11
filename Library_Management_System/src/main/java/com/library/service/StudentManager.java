package com.library.service;

import com.library.entity.Student;
import com.library.mappers.StudentMappers;
import com.library.util.InputUtil;
import com.library.util.SqlUtil;

import java.util.List;

public class StudentManager {
    public static void addStudent() {
        System.out.println("========= 录入学生信息 =========");
        int sid = InputUtil.inputInt("请输入要录入学生的学号：");
        // 检查学号是否已存在
        if (isStudentIdExists(sid)) {
            System.out.println("该学号已存在，无法录入！");
            return;
        }
        String name = InputUtil.inputString("请输入要录入学生的名称：");
        int age = InputUtil.inputInt("请输入要录入学生的年龄：");
        String gender = InputUtil.inputString("请输入要录入学生的性别(男/女)：", "男", "女");
        Student student = new Student(sid, name, age, gender);
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            int count = mapper.insertStudent(student);
            if (count > 0) {
                System.out.printf("学生信息录入成功！\n学号：%d\n姓名：%s\n年龄：%d\n性别：%s\n",
                        student.getSid(), student.getName(), student.getAge(), student.getGender());
            } else {
                System.out.println("学生信息录入失败！");
            }
        });
    }
    public static void deleteStudent() {
        System.out.println("========= 删除学生信息 =========");
        int sid = InputUtil.inputInt("请输入要删除学生的学号：");
        // 检查学号是否存在
        if (!isStudentIdExists(sid)) {
            System.out.println("该学号不存在，无法删除！");
            return;
        }
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            int count = mapper.deleteStudentById(sid);
            if (count > 0) {
                System.out.println("删除学生信息成功！");
            } else {
                System.out.println("删除学生信息失败！");
            }
        });
    }
    public static void updateStudent() {
        System.out.println("========= 修改学生信息 =========");
        int sid = InputUtil.inputInt("请输入要修改学生的学号：");
        // 检查学号是否存在
        if (!isStudentIdExists(sid)) {
            System.out.println("该学号不存在，无法修改！");
            return;
        }
        String name = InputUtil.inputString("请输入要修改学生的名称：");
        int age = InputUtil.inputInt("请输入要修改学生的年龄：");
        String gender = InputUtil.inputString("请输入要修改学生的性别(男/女)：", "男", "女");
        Student student = new Student(sid, name, age, gender);
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            int count = mapper.updateStudent(student);
            if (count > 0) {
                System.out.println("修改学生信息成功！");
            } else {
                System.out.println("修改学生信息失败！");
            }
        });
    }
    public static void queryStudent() {
        while (true) {
            System.out.println("========= 查询学生信息 =========");
            System.out.println("1、根据学号查询学生信息");
            System.out.println("2、列出所有学生信息");
            String input = InputUtil.inputString("请输入上面功能对应的序号（输入其他内容退出系统）");
            switch (input) {
                case "1":
                    queryStudentBySid();
                    break;
                case "2":
                    listStudent();
                    break;
                default:
                    System.out.println("感谢使用学生查询，再见！");
                    return;
            }
        }
    }
    public static void queryStudentBySid() {
        System.out.println("========= 根据学号查询学生信息 =========");
        int sid = InputUtil.inputInt("请输入要查询学生的学号：");
        // 检查学号是否存在
        if (!isStudentIdExists(sid)) {
            System.out.println("该学号不存在，无法查询！");
            return;
        }
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            Student student = mapper.selectStudentById(sid);
            if (student != null) {
                System.out.println("查询到学生信息：");
                System.out.printf("学号：%d\n姓名：%s\n年龄：%d\n性别：%s\n",
                        student.getSid(), student.getName(), student.getAge(), student.getGender());
            } else {
                System.out.println("未查询到学生信息！");
            }
        });
    }
    public static void listStudent() {
        System.out.println("========= 学生信息列表 =========");
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            List<Student> students = mapper.selectAllStudent();
            if (!students.isEmpty()) {
                System.out.println("学生信息列表：");
                for (Student student : students) {
                    System.out.printf("学号：%d\n姓名：%s\n年龄：%d\n性别：%s\n",
                            student.getSid(), student.getName(), student.getAge(), student.getGender());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("暂无学生信息！");
            }
        });
    }
    // 检查学号是否存在
    private static boolean isStudentIdExists(int sid) {
        final boolean[] exists = {false};
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            Student student = mapper.selectStudentById(sid);
            exists[0] = student != null;
        });
        return exists[0];
    }
}
