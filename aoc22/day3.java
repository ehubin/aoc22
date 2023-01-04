package aoc22;

import java.util.stream.Stream;

public class day3 extends b{
    int res=0;
    public static void main(String[] a) {
        day3 d=new day3();
        try {
            //d.pt1();
            d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }


    int getVal(char c) {return Character.isUpperCase(c)? c-'A'+27 : c-'a'+1;}
    @Override
    void processP1(Stream<String> s) {
        final int[] score={0};

        s.forEach(str -> {
            int b[] = new int[53];
            for(int i=0;i<str.length()/2;++i) b[getVal(str.charAt(i))]++;
            for(int i=str.length()/2;i<str.length();++i) {
                int v=getVal(str.charAt(i));
                if(b[v]>0) {
                    score[0]+=v;
                    b[v]--;
                    System.out.println(str+" "+str.charAt(i));
                    break;
                }
            }

        });
        System.out.println(score[0]);
    }


    @Override
    void processP2(Stream<String> s) {
        final int[] score={0},c={0};
        boolean b[][]={{false},{false},{false}};
        s.forEach(str -> {
            int idx=c[0]%3;
            c[0]++;
            b[idx]=new boolean[53];
            for(int i=0;i<str.length();++i) b[idx][getVal(str.charAt(i))]=true;
            if(idx==2) {
               for(int i=1;i<53;++i) if(b[0][i] && b[1][i] && b[2][i]) {
                   score[0]+=i;
                   break;
               }
            }
        });
        System.out.println(score[0]);
    }
}
