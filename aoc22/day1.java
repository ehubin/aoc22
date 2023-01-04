package aoc22;

import org.apache.commons.lang.mutable.MutableBoolean;

import java.lang.ref.Reference;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class day1 extends b{
    final int[] cookie = new int[1000];
    final AtomicInteger max=new AtomicInteger(Integer.MIN_VALUE);
    public static void main(String[] a) {
        day1 d=new day1();
        try {
            // part 1 with local text input
            //d.p1_t();
            // part 1 with remote http input
            //d.p1_h();
            //part 2 with local text input
            //d.p2_t();
            //part 2 with remote http input
            d.p2_h();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void readInput(Stream<String> in) {
        final AtomicInteger cur = new AtomicInteger(0);
        in.forEach(str -> {
            if(str.length()==0) {
                int c=cur.getAndIncrement();
                if(max.get() <cookie[c]) max.set(cookie[c]);
            } else {
                cookie[cur.get()]+=Integer.parseInt(str);
            }
        });
    }

    @Override
    void P1() {
        System.out.println(max);
    }

    @Override
    void P2() {
        Arrays.sort(cookie);
        int l=cookie.length;
        System.out.println(cookie[l-1]+cookie[l-2]+cookie[l-3]);
    }
}
