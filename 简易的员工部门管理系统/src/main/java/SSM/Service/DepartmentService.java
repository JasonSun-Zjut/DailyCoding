package SSM.Service;

import SSM.Bean.Department;
import SSM.Dao.DepartmentMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @date /08/30
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    @RequiresPermissions("DepartmentQuery")
    public List<Department> getDepartments()
    {
        List<Department> departmentList=departmentMapper.selectByExample(null);
        return departmentList;
    }
    @RequiresPermissions("DepartmentInsert")
    public void insertDepartment(Department department)
    {
        departmentMapper.insert(department);
    }
    @RequiresPermissions("DepartmentDelete")
    public void deleteDepartment(int departmentId)
    {
        departmentMapper.deleteByPrimaryKey(departmentId);
    }
    @RequiresPermissions("DepartmentUpdate")
    public void updateDepartment(Department department)
    {
        departmentMapper.updateByPrimaryKey(department);
    }
}
