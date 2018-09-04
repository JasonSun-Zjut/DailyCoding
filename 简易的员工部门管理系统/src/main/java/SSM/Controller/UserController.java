package SSM.Controller;

import SSM.Bean.User;
import SSM.JsonMsg;
import SSM.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date /09/01
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public String logincheck(HttpServletRequest request)
    {
        String exceptionClassName= (String) request.getAttribute("shiroLoginFailure");
        System.out.println(exceptionClassName);
        if(exceptionClassName!=null)
        {
            if(exceptionClassName.equals(UnknownAccountException.class.getName())){
                request.setAttribute("errorMsg","账号不存在");
            }else  if (exceptionClassName.equals(IncorrectCredentialsException.class.getName()))
            {
                request.setAttribute("errorMsg","用户名或密码错误");
            }
        }
        return "login";
    }
    @ResponseBody
    @RequestMapping("/insertUser")
    public JsonMsg insertUser(User user)
    {
        userService.insertUser(user);
        return JsonMsg.success();
    }
}
