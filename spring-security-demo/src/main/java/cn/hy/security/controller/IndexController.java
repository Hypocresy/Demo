package cn.hy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/8 10:53
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/content")
    public String content() {
        return "content";
    }

}
