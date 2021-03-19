package cn.hy.prometheus.compont;

import java.lang.ref.PhantomReference;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/23 14:21
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(1611043538572L);
        System.out.println(sdf.format(date));
        System.out.println(System.currentTimeMillis());
        LocalDate now = LocalDate.now();
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getMonth().getValue());
        PhantomReference<String> dasdas = new PhantomReference<>("dasdas",null);
//        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
//        linkedHashMap.put("asdasd","dasd");

    }
}
