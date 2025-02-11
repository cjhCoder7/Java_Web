import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.Date;

public class MyFormatter extends Formatter {
    @Override
    public String format(java.util.logging.LogRecord record) {
        StringBuilder builder = new StringBuilder();

        // 日期
        Date data = new Date(record.getMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.append(dateFormat.format(data));
        // 日志级别
        builder.append(" ").append(record.getLevel());
        builder.append(" --- ");
        // 线程名称
        builder.append('[').append(Thread.currentThread().getName()).append(']');
        // 类名称
        builder.append(" ").append(String.format("%-15s", record.getSourceClassName()));
        // 消息内容
        builder.append(" : ").append(record.getMessage());

        return builder.toString();
    }
}
