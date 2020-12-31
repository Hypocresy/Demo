package cn.hy.prometheus.compont;

import cn.hy.prometheus.entity.Tag;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/12/8 16:19
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
public class TestGc {
    public static void main(String[] args) {
        Tag tag = new Tag();
        Tag tag1 = new Tag();
        tag.setTag(tag1);
        tag1.setTag(tag);
        //软引用
        SoftReference<String> dasda = new SoftReference<>("dasda");
        //弱引用
        WeakReference<String> sadasd = new WeakReference<>("sadasd");
        tag= null;
        tag1=null;
        System.gc();
//        Runtime.getRuntime().gc();
    }
}
