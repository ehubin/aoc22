package aoc22;

import java.util.Arrays;
import java.util.stream.Stream;

public class day2 extends b{
    int res=0;
    public static void main(String[] a) {
        day2 d=new day2();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }



    @Override
    void processP1(Stream<String> s) {
        final int[] score={0};
        int res[][] = { {3,6,0},{0,3,6},{6,0,3}};
        s.forEach(str -> {
            int p1=str.charAt(0)-'A',p2=str.charAt(2)-'X';
            score[0] += p2+1+res[p1][p2];
        });

        System.out.println(score[0]);
    }


    @Override
    void processP2(Stream<String> s) {
        final int[] score={0};
        int res[][] = { {3,1,2},{1,2,3},{2,3,1}};
        s.forEach(str -> {
            int p1=str.charAt(0)-'A',p2=str.charAt(2)-'X';
            score[0] += 3*p2+res[p1][p2];
        });

        System.out.println(score[0]);
    }
}
