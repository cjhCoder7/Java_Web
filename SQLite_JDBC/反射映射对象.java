import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class 反射映射对象 {
    public static void main(String[] args) throws InstantiationException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        String url = "jdbc:sqlite:test.db";

        // 查询数据库并映射到 Company 对象
        List<Company> companies = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY")) {

            // 使用泛型方法将 ResultSet 映射到 Company 对象
            companies = convert(rs, Company.class);

        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Database error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        // 打印映射后的对象
        for (Company company : companies) {
            System.out.println(company);
        }

        System.out.println("Operation done successfully");
    }

    /**
     * 泛型方法，用于将 ResultSet 中的数据映射到指定的实体类对象列表中。
     *
     * @param rs    ResultSet 对象
     * @param clazz 需要映射的目标类
     * @param <T>   目标类的泛型类型
     * @return 包含映射对象的列表
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     */
    public static <T> List<T> convert(ResultSet rs, Class<T> clazz)
            throws SQLException, IllegalAccessException, InstantiationException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        List<T> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        while (rs.next()) {
            T obj = clazz.getDeclaredConstructor().newInstance(); // 创建目标类的新实例

            for (Field field : fields) {
                String fieldName = field.getName();
                Object value = rs.getObject(fieldName); // 根据字段名从 ResultSet 中获取值

                // 设置字段为可访问
                field.setAccessible(true);
                // 将值设置到对象中
                field.set(obj, value);
            }

            result.add(obj);
        }

        return result;
    }
}