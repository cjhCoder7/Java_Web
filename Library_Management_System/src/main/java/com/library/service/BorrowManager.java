package com.library.service;

import com.library.entity.Book;
import com.library.entity.Borrow;
import com.library.entity.Student;
import com.library.mappers.BookMappers;
import com.library.mappers.BorrowMappers;
import com.library.mappers.StudentMappers;
import com.library.util.InputUtil;
import com.library.util.SqlUtil;

import java.util.List;

public class BorrowManager {
    public static void addBorrow() {
        System.out.println("========= 录入借阅信息 =========");
        int sid = InputUtil.inputInt("请输入借阅人的学号：");
        int bid = InputUtil.inputInt("请输入借阅书籍的编号：");
        // 检查学号和书籍编号是否存在
        if (!isStudentIdExists(sid) || !isBookIdExists(bid)) {
            System.out.println("学号或书籍编号不存在，无法录入借阅信息！");
            return;
        }
        String borrowDate = InputUtil.inputString("请输入借阅日期：");
        String returnDate = InputUtil.inputString("请输入归还日期：");

        Borrow borrow = new Borrow(sid, bid, borrowDate, returnDate);
        SqlUtil.doSqlWork(BorrowMappers.class, mapper -> {
            int count = mapper.insertBorrow(borrow);
            if (count > 0) {
                System.out.printf("借阅信息录入成功！\n学号：%d\n书籍编号：%d\n借阅日期：%s\n归还日期：%s\n",
                        borrow.getSid(), borrow.getBid(), borrow.getBorrowDate(), borrow.getReturnDate());
            } else {
                System.out.println("借阅信息录入失败！");
            }
        });
    }

    public static void deleteBorrow() {
        System.out.println("========= 删除借阅信息 =========");
        int sid = InputUtil.inputInt("请输入借阅人的学号：");
        int bid = InputUtil.inputInt("请输入借阅书籍的编号：");
        // 检查学号和书籍编号是否存在
        if (!isStudentIdExists(sid) || !isBookIdExists(bid)) {
            System.out.println("学号或书籍编号不存在，无法删除借阅信息！");
            return;
        }
        SqlUtil.doSqlWork(BorrowMappers.class, mapper -> {
            int count = mapper.deleteBorrow(sid, bid);
            if (count > 0) {
                System.out.println("借阅信息删除成功！");
            } else {
                System.out.println("借阅信息删除失败！");
            }
        });
    }

    public static void updateBorrow() {
        System.out.println("========= 更新借阅信息 =========");
        int sid = InputUtil.inputInt("请输入借阅人的学号：");
        int bid = InputUtil.inputInt("请输入借阅书籍的编号：");
        // 检查学号和书籍编号是否存在
        if (!isStudentIdExists(sid) || !isBookIdExists(bid)) {
            System.out.println("学号或书籍编号不存在，无法更新借阅信息！");
            return;
        }
        String borrowDate = InputUtil.inputString("请输入借阅日期：");
        String returnDate = InputUtil.inputString("请输入归还日期：");

        Borrow borrow = new Borrow(sid, bid, borrowDate, returnDate);
        SqlUtil.doSqlWork(BorrowMappers.class, mapper -> {
            int count = mapper.updateBorrow(borrow);
            if (count > 0) {
                System.out.println("借阅信息修改成功！");
            } else {
                System.out.println("借阅信息修改失败！");
            }
        });
    }

    public static void queryBorrow() {
        while (true) {
            System.out.println("========= 查询借阅情况 =========");
            System.out.println("1. 根据学号查询借阅情况");
            System.out.println("2. 根据书籍编号查询借阅情况");
            System.out.println("3. 查看所有借阅信息");
            String input = InputUtil.inputString("请输入上面功能对应的序号（输入其他内容退出系统）");
            switch (input) {
                case "1":
                    queryBorrowBySid();
                    break;
                case "2":
                    queryBorrowByBid();
                    break;
                case "3":
                    listBorrow();
                    break;
                default:
                    System.out.println("感谢使用借阅查询，再见！");
                    return;
            }
        }
    }

    public static void queryBorrowBySid() {
        System.out.println("========= 根据学号查询借阅情况 =========");
        int sid = InputUtil.inputInt("请输入借阅人的学号：");
        // 检查学号是否存在
        if (!isStudentIdExists(sid)) {
            System.out.println("该学号不存在，无法查询借阅信息！");
            return;
        }
        SqlUtil.doSqlWork(BorrowMappers.class, mapper -> {
            // 获取该学生的借阅信息列表
            List<Borrow> borrowList = mapper.selectBorrowBySid(sid);
            if (!borrowList.isEmpty()) {
                System.out.println("借阅情况如下：");
                // 获取学生的详细信息
                Student student = getStudentById(sid);
                if (student != null) {
                    System.out.printf("学生信息：\n学号：%d\n姓名：%s\n", student.getSid(), student.getName());
                } else {
                    System.out.println("未找到该学生信息！");
                }
                for (Borrow borrow : borrowList) {
                    // 获取书籍的详细信息
                    Book book = getBookById(borrow.getBid());
                    if (book != null) {
                        System.out.printf("书籍信息：\n编号：%d\n标题：%s\n作者：%s\n出版社：%s\n描述：%s\n",
                                book.getBid(), book.getTitle(), book.getAuthor(), book.getPress(), book.getDescription());
                    } else {
                        System.out.println("未找到该书籍信息！");
                    }
                    System.out.printf("借阅信息：\n学号：%d\n书籍编号：%d\n借阅日期：%s\n归还日期：%s\n",
                            borrow.getSid(), borrow.getBid(), borrow.getBorrowDate(), borrow.getReturnDate());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("暂无借阅信息！");
            }
        });
    }
    public static void queryBorrowByBid() {
        System.out.println("========= 根据书籍编号查询借阅情况 =========");
        int bid = InputUtil.inputInt("请输入借阅书籍的编号：");
        // 检查书籍编号是否存在
        if (!isBookIdExists(bid)) {
            System.out.println("该书籍编号不存在，无法查询借阅信息！");
            return;
        }
        SqlUtil.doSqlWork(BorrowMappers.class, mapper -> {
            // 获取该书籍的借阅信息列表
            List<Borrow> borrowList = mapper.selectBorrowByBid(bid);
            if (!borrowList.isEmpty()) {
                System.out.println("借阅情况如下：");
                // 获取书籍的详细信息
                Book book = getBookById(bid);
                if (book != null) {
                    System.out.printf("书籍信息：\n编号：%d\n标题：%s\n作者：%s\n出版社：%s\n描述：%s\n",
                            book.getBid(), book.getTitle(), book.getAuthor(), book.getPress(), book.getDescription());
                } else {
                    System.out.println("未找到该书籍信息！");
                }
                for (Borrow borrow : borrowList) {
                    // 获取学生的详细信息
                    Student student = getStudentById(borrow.getSid());
                    if (student != null) {
                        System.out.printf("学生信息：\n学号：%d\n姓名：%s\n", student.getSid(), student.getName());
                    } else {
                        System.out.println("未找到该学生信息！");
                    }
                    System.out.printf("借阅信息：\n学号：%d\n书籍编号：%d\n借阅日期：%s\n归还日期：%s\n",
                            borrow.getSid(), borrow.getBid(), borrow.getBorrowDate(), borrow.getReturnDate());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("暂无借阅信息！");
            }
        });
    }
    // 根据学生ID获取学生详细信息
    private static Student getStudentById(int sid) {
        final Student[] student = {null};
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            student[0] = mapper.selectStudentById(sid);
        });
        return student[0];
    }
    // 根据书籍ID获取书籍详细信息
    private static Book getBookById(int bid) {
        final Book[] book = {null};
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            book[0] = mapper.selectBookById(bid);
        });
        return book[0];
    }

    public static void listBorrow() {
        System.out.println("========= 借阅信息列表 =========");
        SqlUtil.doSqlWork(BorrowMappers.class, borrowMapper -> {
            // 获取所有借阅信息
            List<Borrow> borrowList = borrowMapper.selectAllBorrow();
            if (!borrowList.isEmpty()) {
                System.out.println("借阅信息如下：");
                for (Borrow borrow : borrowList) {
                    int sid = borrow.getSid();
                    int bid = borrow.getBid();
                    // 根据 sid 获取学生详细信息
                    Student student = getStudentById(sid);
                    // 根据 bid 获取书籍详细信息
                    Book book = getBookById(bid);
                    // 输出学生详细信息
                    if (student != null) {
                        System.out.printf("学生信息：\n学号：%d\n姓名：%s\n", student.getSid(), student.getName());
                    } else {
                        System.out.println("未找到学号为 " + sid + " 的学生信息！");
                    }
                    // 输出书籍详细信息
                    if (book != null) {
                        System.out.printf("书籍信息：\n编号：%d\n标题：%s\n作者：%s\n出版社：%s\n描述：%s\n",
                                book.getBid(), book.getTitle(), book.getAuthor(), book.getPress(), book.getDescription());
                    } else {
                        System.out.println("未找到编号为 " + bid + " 的书籍信息！");
                    }
                    // 输出借阅信息
                    System.out.printf("借阅信息：\n学号：%d\n书籍编号：%d\n借阅日期：%s\n归还日期：%s\n",
                            borrow.getSid(), borrow.getBid(), borrow.getBorrowDate(), borrow.getReturnDate());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("暂无借阅信息！");
            }
        });
    }

    // 检查学生编号是否存在
    private static boolean isStudentIdExists(int sid) {
        final boolean[] exists = {false};
        SqlUtil.doSqlWork(StudentMappers.class, mapper -> {
            exists[0] = mapper.selectStudentById(sid) != null;
        });
        return exists[0];
    }

    // 检查书籍编号是否存在
    private static boolean isBookIdExists(int bid) {
        final boolean[] exists = {false};
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            exists[0] = mapper.selectBookById(bid) != null;
        });
        return exists[0];
    }
}