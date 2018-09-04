package SSM.Service;

import SSM.Bean.Department;
import SSM.Bean.Employee;
import SSM.Bean.EmployeeExample;
import SSM.Dao.EmployeeMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date /08/29
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    /**
     * 查询所有员工
     * */
    @RequiresPermissions("EmployQuery")
    public List<Employee> getAll() {
          return employeeMapper.selectByExampleWithDept(null);
    }
    @RequiresPermissions("EmployInsert")
    public void addEmployee(Employee employee){
        employeeMapper.insert(employee);
    }
    @RequiresPermissions("EmployDelete")
    public void deleteEmployee(int employId)
    {
        employeeMapper.deleteByPrimaryKey(employId);
    }
    @RequiresPermissions("EmployUpdate")
    public void updateEmploy(Employee employee)
    {
        employeeMapper.updateByPrimaryKey(employee);
    }
    public boolean isEmpExist(String empName)
    {
        EmployeeExample employee=new EmployeeExample();
        EmployeeExample.Criteria criteria=employee.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count=employeeMapper.countByExample(employee);
        return count==0;
    }
}
