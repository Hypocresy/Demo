package src.main.java.com.hy.demo;

import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/8/6 14:32
 * @since 0.0.1
 */

public class LockTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List arrayList = new CopyOnWriteArrayList();
        new HashMap<>();
        HashSet<Object> hashSet = new HashSet<>();
        ArrayList<Object> list = new ArrayList<>();
        FutureTask<Integer> future = new FutureTask<>(() ->  1
        );
        new Thread(future).start();
        Integer integer = future.get();



    }
    class porudct{
        int total;
        public  void  consumer(){
            total--;
            System.out.println();
        }
    }

    @Test
    public void asdasd(){
        int t;
        Integer [] al = {12,10,16,48,3,5,7};
//      for (int i=0;i<al.length;i++){
//          for (int j=i+1;j<al.length;j++){
//              if(al[i]>al[j]){
//                 t = al[i];
//                 al[i]=al[j] ;
//                 al[j] =t;
//              }
//          }
//        }
//        Arrr arrr = new Arrr();
//        Arrays.sort(al,arrr);
//       Arrays.sort(al);
        Arrays.sort(al,Collections.reverseOrder());
       for(int a:al){
           System.out.println(a);
       }

    }
    class Arrr implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {

            return o2-01;
        }


    }
}
