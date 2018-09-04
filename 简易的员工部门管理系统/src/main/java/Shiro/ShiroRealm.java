package Shiro;

import SSM.Bean.Role;
import SSM.Bean.User;
import SSM.Bean.UserPermission;
import SSM.Service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date /09/01
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        User user = userService.getUser(username);
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码错误");
        }
        /**认证的实体信息*/
        Object principal = user.getUsername();
        /**credentials密码：*/
        Object credentials = user.getPassword();
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        return info;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        User user=userService.getRole(username);
        String rolename=user.getRole().getName();
        int id=user.getRole().getId();
        Role role=userService.getPermission(id);
        List<UserPermission> permissions=role.getUserPermissions();
        List<String> permission=new ArrayList<>();
        for(UserPermission userPermission:permissions)
        {
            permission.add(userPermission.getUserpermission());
        }
        info.addRole(rolename);
        info.addStringPermissions(permission);
        return info;
    }
}
