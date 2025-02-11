package com.library.service;

import com.library.entity.Book;
import com.library.mappers.BookMappers;
import com.library.util.InputUtil;
import com.library.util.SqlUtil;

import java.util.List;

public class BookManager {
    public static void addBook() {
        System.out.println("========= 录入书籍信息 =========");
        int bid = InputUtil.inputInt("请输入要录入书籍的编号：");
        // 检查书籍编号是否已存在
        if (isBookIdExists(bid)) {
            System.out.println("该书籍编号已存在，无法录入！");
            return;
        }
        String title = InputUtil.inputString("请输入要录入书籍的标题：");
        String author = InputUtil.inputString("请输入要录入书籍的作者：");
        String press = InputUtil.inputString("请输入要录入书籍的出版社：");
        String description = InputUtil.inputString("请输入要录入书籍的描述：");

        Book book = new Book(bid, title, author, press, description);
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            int count = mapper.insertBook(book);
            if (count > 0) {
                System.out.printf("书籍信息录入成功！\n编号：%d\n标题：%s\n作者：%s\n出版社：%s\n描述：%s\n",
                        book.getBid(), book.getTitle(), book.getAuthor(), book.getPress(), book.getDescription());
            } else {
                System.out.println("书籍信息录入失败！");
            }
        });
    }

    public static void deleteBook() {
        System.out.println("========= 删除书籍信息 =========");
        int bid = InputUtil.inputInt("请输入要删除书籍的编号：");
        // 检查书籍编号是否存在
        if (!isBookIdExists(bid)) {
            System.out.println("该书籍编号不存在，无法删除！");
            return;
        }
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            int count = mapper.deleteBookById(bid);
            if (count > 0) {
                System.out.println("书籍信息删除成功！");
            } else {
                System.out.println("书籍信息删除失败！");
            }
        });
    }

    public static void updateBook() {
        System.out.println("========= 修改书籍信息 =========");
        int bid = InputUtil.inputInt("请输入要修改书籍的编号：");
        // 检查书籍编号是否存在
        if (!isBookIdExists(bid)) {
            System.out.println("该书籍编号不存在，无法修改！");
            return;
        }
        String title = InputUtil.inputString("请输入要修改书籍的标题：");
        String author = InputUtil.inputString("请输入要修改书籍的作者：");
        String press = InputUtil.inputString("请输入要修改书籍的出版社：");
        String description = InputUtil.inputString("请输入要修改书籍的描述：");
        Book book = new Book(bid, title, author, press, description);
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            int count = mapper.updateBook(book);
            if (count > 0) {
                System.out.println("书籍信息修改成功！");
            } else {
                System.out.println("书籍信息修改失败！");
            }
        });
    }

    public static void queryBook() {
        while (true) {
            System.out.println("========= 查询书籍信息 =========");
            System.out.println("1、根据编号查询书籍信息");
            System.out.println("2、列出所有书籍信息");
            String input = InputUtil.inputString("请输入上面功能对应的序号（输入其他内容退出系统）");
            switch (input) {
                case "1":
                    queryBookByBid();
                    break;
                case "2":
                    listBook();
                    break;
                default:
                    System.out.println("感谢使用书籍查询，再见！");
                    return;
            }
        }
    }

    public static void queryBookByBid() {
        System.out.println("========= 根据编号查询书籍信息 =========");
        int bid = InputUtil.inputInt("请输入要查询书籍的编号：");
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            Book book = mapper.selectBookById(bid);
            if (book != null) {
                System.out.println("查询到书籍信息：");
                System.out.printf("编号：%d\n标题：%s\n作者：%s\n出版社：%s\n描述：%s\n",
                        book.getBid(), book.getTitle(), book.getAuthor(), book.getPress(), book.getDescription());
            } else {
                System.out.println("未查询到书籍信息！");
            }
        });
    }

    public static void listBook() {
        System.out.println("========= 书籍信息列表 =========");
        SqlUtil.doSqlWork(BookMappers.class, mapper -> {
            List<Book> books = mapper.selectAllBook();
            if (!books.isEmpty()) {
                System.out.println("书籍信息列表：");
                for (Book book : books) {
                    System.out.printf("编号：%d\n标题：%s\n作者：%s\n出版社：%s\n描述：%s\n",
                            book.getBid(), book.getTitle(), book.getAuthor(), book.getPress(), book.getDescription());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("暂无书籍信息！");
            }
        });
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