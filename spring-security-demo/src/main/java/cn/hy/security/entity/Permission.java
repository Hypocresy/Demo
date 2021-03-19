package cn.hy.security.entity;

import java.io.Serializable;

/**
 * (Permission)实体类
 *
 * @author makejava
 * @since 2021-02-22 09:46:10
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = 828800499075656773L;

    private Integer id;
    /**
     * 权限名
     */
    private String name;
    /**
     * 资源url
     */
    private String url;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 上级
     */
    private Integer parent;
    /**
     * 备注
     */
    private String remarks;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}