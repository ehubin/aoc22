package aoc22;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class day6 extends b{
    int res=0;
    public static void main(String[] a) {
        day6 d=new day6();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void processP1(Stream<String> s) {
        s.forEach(str -> {
            int i=0;
            while(true) {
                char c1=str.charAt(i);
                char c2=str.charAt(i+1);
                char c3=str.charAt(i+2);
                char c4=str.charAt(i+3);

                if(c1 != c2 && c1 != c3 && c1 != c4 && c2 != c3 && c2 != c4 && c3 != c4) {
                    System.out.println(i+4);
                    break;
                }
                ++i;
            }
        });
    }


    @Override
    void processP2(Stream<String> s) {

        s.forEach(str -> {
            int i=0;
            char[] c=new char[14];
            loop:
            while(true) {
                for(int j=0;j<14;++j) c[j] = str.charAt(i+j);

                for(int j=0;j<13;++j)
                    for(int k=j+1;k<14;++k)
                        if(c[j]==c[k]) { ++i;continue loop;}

                System.out.println(i+14);
                break;
            }
        });

    }
}
