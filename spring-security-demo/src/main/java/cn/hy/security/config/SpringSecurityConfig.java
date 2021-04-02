package cn.hy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/8 11:18
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 配置那些访问不许要权限校验
                .antMatchers("/resources/**", "/").permitAll()
                //配置 路径访问认证
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/content/**").access("hasRole('ADMIN') or hasRole('USER')")
                //其余访问路径 都需要认证
                .anyRequest().authenticated()
                .and()
                //登录设置
                .formLogin()
//                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().ignoringAntMatchers("/logout");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    }

}
