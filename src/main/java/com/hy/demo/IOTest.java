package com.hy.demo;

import com.hy.demo.pojo.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/12/25 16:15
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public class IOTest {
    public static void main(String[] args) throws IOException {
        User user = new User();
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("sdasd"));
        stream.writeObject(user);
    }
}
