import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface CompanyMappersZhuJie {

    @Select("select * from COMPANY")
    List<Company> selectAllCompany();

    @Select("select * from COMPANY where id = #{id}")
    Company selectCompanyById(int id);
}
