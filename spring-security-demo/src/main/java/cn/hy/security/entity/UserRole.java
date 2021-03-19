package cn.hy.security.entity;

import java.io.Serializable;

/**
 * (UserRole)实体类
 *
 * @author makejava
 * @since 2021-02-22 09:46:13
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = 606006252802152949L;

    private Integer id;

    private Integer userId;

    private Integer roleId;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}