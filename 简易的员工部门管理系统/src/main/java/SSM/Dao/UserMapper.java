package SSM.Dao;

import SSM.Bean.Role;
import SSM.Bean.User;

public interface UserMapper {
    public User selectUser(String username);
    public User selectUserWithRole(String username);
    public void insertUser(User user);
    public Role selectPermission(int id);
}
