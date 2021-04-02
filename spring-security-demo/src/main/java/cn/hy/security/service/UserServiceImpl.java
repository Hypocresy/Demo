package cn.hy.security.service;

import cn.hy.security.dao.UserDao;
import cn.hy.security.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/3/19 17:16
 * @since 0.0.1
 * 说明
 */
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User quser = new User();
        quser.setName(username);
        List<User> users = userDao.queryAll(quser);
        if(users.size()>1||users.size()==0){
             throw new  UsernameNotFoundException("用户不存在或存在多条记录");
        }
        User dasda = JSONObject.parseObject("dasda", User.class);
        return quser;
    }
}
