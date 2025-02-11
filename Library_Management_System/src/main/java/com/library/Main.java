package com.library;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

import com.library.service.*;
import com.library.util.SqlUtil;
import org.apache.ibatis.io.Resources;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(Resources.getResourceAsStream("logging.properties"));
        SqlUtil.openSqlSession();

        while (true) {
            System.out.println("========= 图书管理系统 =========");
            System.out.println("1、录入学生信息");
            System.out.println("2、录入书籍信息");
            System.out.println("3、录入借阅信息");
            System.out.println("4、查询学生信息");
            System.out.println("5、查询书籍信息");
            System.out.println("6、查询借阅情况");
            System.out.println("7、删除学生信息");
            System.out.println("8、删除书籍信息");
            System.out.println("9、删除借阅信息");
            System.out.println("10、更新学生信息");
            System.out.println("11、更新书籍信息");
            System.out.println("12、更新借阅信息");
            System.out.println("请输入上面功能对应的序号（输入其他内容退出系统）");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    StudentManager.addStudent();
                    break;
                case "2":
                    BookManager.addBook();
                    break;
                case "3":
                    BorrowManager.addBorrow();
                    break;
                case "4":
                    StudentManager.queryStudent();
                    break;
                case "5":
                    BookManager.queryBook();
                    break;
                case "6":
                    BorrowManager.queryBorrow();
                    break;
                case "7":
                    StudentManager.deleteStudent();
                    break;
                case "8":
                    BookManager.deleteBook();
                    break;
                case "9":
                    BorrowManager.deleteBorrow();
                    break;
                case "10":
                    StudentManager.updateStudent();
                    break;
                case "11":
                    BookManager.updateBook();
                    break;
                case "12":
                    BorrowManager.updateBorrow();
                    break;
                default:
                    System.out.println("感谢使用，再见！");
                    SqlUtil.closeSqlSession();
                    scanner.close();
                    return;
            }
        }
    }
}
