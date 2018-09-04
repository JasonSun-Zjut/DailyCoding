package SSM.Controller;

import SSM.Bean.Department;
import SSM.Bean.Employee;
import SSM.JsonMsg;
import SSM.Service.DepartmentService;
import SSM.Service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date /08/29
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;

    /**
     * 分页查询数据
     */
    @RequestMapping("/emps")
    public ModelAndView getEmps(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, Model model) {
        PageHelper.startPage(pageNum,5);
        List<Employee> employeeLists = employeeService.getAll();
        PageInfo<Employee> pageInfo=new PageInfo<>(employeeLists);
       ModelAndView modelAndView=new ModelAndView("list");
       modelAndView.addObject("PageInfo",pageInfo);
       return modelAndView;
    }
    @RequestMapping("/empsJson")
    @ResponseBody
    public JsonMsg getEmpsJson(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum)
    {
        PageHelper.startPage(pageNum,5);
        List<Employee> employeeLists=employeeService.getAll();
        PageInfo<Employee> pageInfo=new PageInfo<>(employeeLists);
        return JsonMsg.success().add("pageInfo",pageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    public JsonMsg saveEmployee(@Valid Employee employee, BindingResult result){
        if(result.hasErrors())
        {   Map<String,Object> map=new HashMap<>();
            List<FieldError> fieldErrors=result.getFieldErrors();
            for(FieldError error:fieldErrors)
            {
                System.out.println("错误字段"+error.getField());
                System.out.println("错误信息"+error.getDefaultMessage());
               map.put(error.getField(),error.getDefaultMessage());
            }
            return JsonMsg.failure().add("errorMap",map);
        }else {
        employeeService.addEmployee(employee);
        return JsonMsg.success().add("msg","员工信息插入成功");}
    }

    @ResponseBody
    @RequestMapping(value = "/empCheck",method = RequestMethod.POST)
    public JsonMsg checkEmp(@RequestParam("empName") String empName)
    {
        boolean flag=employeeService.isEmpExist(empName);
        if(flag)
        {
            return JsonMsg.success();
        }
        return JsonMsg.failure();
    }

    @RequestMapping(value = "/info")
    public void getInfo()
    {

    }
}
