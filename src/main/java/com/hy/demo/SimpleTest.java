package com.hy.demo;

import org.junit.Test;
import java.io.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author hy
 * @blame Development Group
 * @date 2020/6/12 8:58
 * @since 0.0.1
 */
public class SimpleTest {
    String [] employees;

    void manageEmployees() {
        int totalEmp = this.employees.length;
        System.out.println("Total employees: " + totalEmp);
        this.report();

    }

    void report() { }

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();


    }

    @Test
    public void iotest() throws IOException {
        byte [] arr = new byte[4096];
        byte [] arr2 = new byte[4096];
        File file = new File("D:\\BaiduNetdiskDownload\\好想爱这个世界啊-棉子.mp3");
        File file1 = new File("D:\\BaiduNetdiskDownload\\好想爱这个世界啊–王贰浪.mp3");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        FileOutputStream outputStream = new FileOutputStream("D:\\BaiduNetdiskDownload\\好想爱这个世界.mp3");

        while(fileInputStream.read(arr)>0){
            outputStream.write(arr);
        }
        while (fileInputStream1.read(arr2)>0){
            outputStream.write(arr2);
        }

        fileInputStream.close();
        fileInputStream1.close();
        outputStream.close();
    }
}
