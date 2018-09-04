package SSM.Service;

import SSM.Bean.Role;
import SSM.Bean.User;
import SSM.Dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date /09/01
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User getUser(String name)
    {
        User user=userMapper.selectUser(name);
        return user;
    }
    public User getRole(String name)
    {
        User user=userMapper.selectUserWithRole(name);
        return user;
    }
    public Role getPermission(int id)
    {
        Role role=userMapper.selectPermission(id);
        return  role;
    }
    public void  insertUser(User user)
    {
        userMapper.insertUser(user);
    }
}
