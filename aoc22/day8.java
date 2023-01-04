package aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class day8 extends b{
    int res,sz,idx;
    int[][] h;
    public static void main(String[] a) {
        day8 d=new day8();
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
            if(sz==0) {
                sz=str.length();
                h=new int[sz][sz];
            }
            for(int i=0;i<sz;++i) h[idx][i]=str.charAt(i)-'0';
            idx++;
        });
        int me;
        boolean noSee1,noSee2,noSee3,noSee4;
        for(int i=1;i<sz-1;++i) {
            for(int j=1;j<sz-1;++j) {
               me=h[i][j];
               noSee1=false;noSee2=false;noSee3=false;noSee4=false;
                for(int k=0; k<i;++k) {
                   if(h[k][j] >=me) {noSee1=true;break;}
                }
                for(int k=i+1; k<sz;++k) {
                    if(h[k][j] >=me) {noSee2=true;break;}
                }
                for(int k=0; k<j;++k) {
                    if(h[i][k] >=me) {noSee3=true;break;}
                }
                for(int k=j+1; k<sz;++k) {
                    if(h[i][k] >=me) {noSee4=true;break;}
                }
                if(noSee1 && noSee2 && noSee3 && noSee4) ++res;

            }
        }
        System.out.println(sz*sz-res);
    }


    @Override
    void processP2(Stream<String> s) {

        s.forEach(str -> {
            if(sz==0) {
                sz=str.length();
                h=new int[sz][sz];
            }
            for(int i=0;i<sz;++i) h[idx][i]=str.charAt(i)-'0';
            idx++;
        });
        int max=0,me,d1=0,d2=0,d3=0,d4=0;

        for(int i=1;i<sz-1;++i) {
            for(int j=1;j<sz-1;++j) {
                me=h[i][j];
                d1=i;d2=sz-1-i;d3=j;d4=sz-1-j;
                for(int k=i-1; k>=0;--k) {
                    if(h[k][j] >=me) {d1=i-k;break;}
                }
                for(int k=i+1; k<sz;++k) {
                    if(h[k][j] >=me) {d2=k-i;break;}
                }
                for(int k=j-1; k>=0;--k) {
                    if(h[i][k] >=me) {d3=j-k;break;}
                }
                for(int k=j+1; k<sz;++k) {
                    if(h[i][k] >=me) {d4=k-j;break;}
                }
                if(d1*d2*d3*d4>max) {
                    max=d1*d2*d3*d4;
                    System.out.println(i+","+j+"\t"+me+"\t"+d1+","+d2+","+d3+","+d4);
                }

            }
        }
        System.out.println(max);

    }
}
