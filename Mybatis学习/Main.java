import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // 如果不自动提交，则会变成事务操作
        SqlSession session = MybatisUtil.openSession(true);

        // 查询后返回对象列表
        List<Company> companies = session.selectList("selectAllCompany");
        companies.forEach(System.out::println);

        // 查询后返回单个对象
        Company company = session.selectOne("selectCompanyById", 1);
        System.out.println(company);

        // 查询后返回Map
        Map<String, Object> map = session.selectOne("selectCompanyById_hashmap", 3);
        System.out.println(map);

        // 查询小于某个值的所有对象
        List<Company> companies1 = session.selectList("selectCompanyByIdLess", 6);
        companies1.forEach(System.out::println);

        // 使用两个参数进行查询，键值对的方式
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("age", 32);
        Company company1 = session.selectOne("selectCompanyByIdAndAge", map1);
        System.out.println(company1);

        // 使用两个参数进行查询，实体类的方式
        Company company2 = session.selectOne("selectCompanyByIdAndAge", new Param(1, 32));
        System.out.println(company2);

        // 插入
        Company company3 = new Company(20, "company10", 10, "address10", 100000);
        int count = session.insert("insertCompany", company3);
        System.out.println("受影响行数" + count);
        System.out.println(company3);

        // 更新
        Company company4 = new Company(20, "company100", 10, "address100", 100000);
        count = session.update("updateCompanyById", company4);
        System.out.println("受影响行数" + count);

        // 删除
        count = session.delete("deleteCompanyById", 20);
        System.out.println("受影响行数" + count);

        // // 使用注解进行操作
        // CompanyMappersZhuJie companyMappersZhuJie =
        // session.getMapper(CompanyMappersZhuJie.class);
        // List<Company> companies2 = companyMappersZhuJie.selectAllCompany();
        // companies2.forEach(System.out::println);

        // session.commit();
        // session.rollback();

        session.close();
    }

    @Data
    @AllArgsConstructor
    static class Param {
        int id;
        int age;
    }
}