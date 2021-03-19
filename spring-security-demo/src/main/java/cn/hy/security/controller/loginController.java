package cn.hy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/8 11:13
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Controller
public class loginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
