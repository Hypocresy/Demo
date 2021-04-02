package cn.hy.demo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/18 14:57
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public class MyclassLoader extends URLClassLoader {
    private String rootPath="class目录";

    public MyclassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public MyclassLoader(URL[] urls) {
        super(urls);
    }

    public MyclassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(rootPath, fileName);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            int i= 0;
            while ((i=inputStream.read())!=-1){
                arrayOutputStream.write(i);
            }
            byte[] bytes = arrayOutputStream.toByteArray();
            inputStream.close();
            arrayOutputStream.close();
            return defineClass(name,bytes,0,bytes.length);

        }catch (Exception e){
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private String getFileName(String fileName){
        int indexOf = fileName.indexOf(".");
        if(indexOf==-1){
            return fileName+".class";
        }else {
            return  fileName.substring(indexOf+1)+".class";
        }
    }
}
