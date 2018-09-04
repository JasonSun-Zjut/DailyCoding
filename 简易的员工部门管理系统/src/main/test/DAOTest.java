import SSM.Bean.Department;
import SSM.Bean.Employee;
import SSM.Dao.DepartmentMapper;
import SSM.Dao.EmployeeMapper;
import SSM.Dao.UserMapper;
import SSM.Service.DepartmentService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author
 * @date /08/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application.xml"})
public class DAOTest {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DepartmentService departmentService;
    @Test
    public void Test()
    {
        /*ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application.xml");
        applicationContext.getBean(DepartmentMapper.class);*/
       //1.插入部门
        /*Department department=new Department();
        department.setDeptName("开发部");
        departmentMapper.insertSelective(department);
        Employee employee=new Employee();
        employee.setEmpId(9);
        employee.setEmpName("31aaf1231");
        employee.setGender("M");
        employeeMapper.insertSelective(employee);*/


    }
}
