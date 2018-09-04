package SSM.Controller;

import SSM.Bean.Department;
import SSM.JsonMsg;
import SSM.Service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author
 * @date /08/30
 */
@Controller
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @RequestMapping("/DeptsJson")
    @ResponseBody
    public JsonMsg getDeptsJson()
    {
        List<Department> departmentList=departmentService.getDepartments();
        return JsonMsg.success().add("departments",departmentList);
    }
    @RequestMapping("/DeptUpdateJson")
    @ResponseBody
    public JsonMsg updateDeptJson(Department department)
    {
        departmentService.updateDepartment(department);
        return JsonMsg.success().add("msg","更新成功");
    }
    @RequestMapping("/DeptDeleteJson")
    @ResponseBody
    public JsonMsg deleteDept(@RequestParam("deptId") int deptId)
    {
        departmentService.deleteDepartment(deptId);
        return JsonMsg.success().add("msg","删除成功");
    }
    @RequestMapping("/DeptInsertJson")
    @ResponseBody
    public JsonMsg insertDept(Department department)
    {
        departmentService.insertDepartment(department);
        return  JsonMsg.success().add("msg","插入成功");
    }

}
