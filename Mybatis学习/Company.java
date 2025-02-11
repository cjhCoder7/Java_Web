import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {
    private int id;
    private String name;
    private int age;
    private String address;
    private float salary;
}
