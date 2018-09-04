package SSM.Bean;

import java.util.List;

/**
 * @author
 * @date /09/02
 */
public class UserPermission {
 private String userpermission;
 private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUserpermission() {
        return userpermission;
    }

    public void setUserpermission(String userpermission) {
        this.userpermission = userpermission;
    }
}
