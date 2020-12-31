package com.example.demo.service;

import com.chenyw.DESedeUtil;
import com.example.demo.entity.Operator;
import com.example.demo.mapper.OperatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/12/4 15:53
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Service
public class Oper {
    @Autowired
    private Environment environment;
    @Autowired
    private OperatorMapper operatorMapper;

@Transactional(rollbackFor = Exception.class)
    public void dddd() throws Exception {
        String secretKey =     environment.getProperty("operator.secretKey");
        byte[] securityKey = DESedeUtil.getSecurityKey(secretKey);
        List<Operator> operators = operatorMapper.selectAll();
      for(Operator item:operators){
//          item.setUsername(DESedeUtil.encryptStr(item.getUsername(),securityKey));
//          item.setRealname(DESedeUtil.encryptStr(item.getRealname(),securityKey));
           item.setEmail(DESedeUtil.encryptStr(item.getEmail(),securityKey));
          operatorMapper.updateByPrimaryKey(item);
      }
    }
}
