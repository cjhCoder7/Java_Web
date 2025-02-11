# JavaWeb学习笔记

## 项目简介

涉及到的技术有：

- Socket
- Junit
- JDBC(SQLite)
- JUL日志系统
- Lombok
- Mybatis
- Maven（构建工具）

最后实现了一个小型的项目——图书馆管理系统(Library_Management_System)，实现了图书的增删改查，用户的增删改查，借阅归还等功能。

项目使用Maven框架管理，使用JDBC连接SQLite数据库，使用Mybatis框架进行数据库操作，使用Junit进行单元测试，使用Lombok进行代码简化，使用JUL日志系统进行日志记录，并没有设计到Socket网络通信

## 项目运行

图书馆管理系统最后被打包成jar包，使用java -jar命令运行，运行后会在工作目录生成Library.db数据库文件，可以使用数据库可视化工具查看内容，另外还有一个system.log日志文件，可以查看日志内容

🔥🔥🔥🔥🔥🔥

```shell
java -jar Library_Management_System-1.0-jar-with-dependencies.jar
```
