package aoc22;

import java.util.Arrays;
import java.util.stream.Stream;

public class day25 extends b{
    long res=0;
    public static void main(String[] a) {
        day25 d=new day25();
        try {
            //d.pt1();
            d.ph1();
            //d.pt2();
            //d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }
    static long toTen(String s) {
        long rad=1,res=0;
        for(int i=s.length()-1;i>=0;--i) {
            res+= rad*toTen(s.charAt(i));
            rad*=5;
        }
        return res;
    }

    static String toSNAFU(long in) {
        char[] s=Long.toString(in,5).toCharArray();
        for(int i=s.length-1;i>0;--i) {
            switch(s[i]) {
                case '3': s[i]='=';++s[i-1]; break;
                case '4': s[i]='-';++s[i-1]; break;
                case '5': s[i]='0';++s[i-1]; break;
                //case '6': s[i]='1';++s[i-1];
            }
        }
        switch(s[0]) {
            case '3': s[0]='=';return "1"+new String(s);
            case '4': s[0]='-';return "1"+new String(s);
            case '5': s[0]='0';return "1"+new String(s);
        }
        return new String(s);
    }
    static int toTen(char c) {
        switch(c) {
            case '2':return 2;
            case '1':return 1;
            case '0':return 0;
            case '-':return -1;
            case '=':return -2;
        }
        throw new IllegalArgumentException("Not valid char "+c);
    }
    @Override
    void processP1(Stream<String> s) {

        s.forEach(str -> {
            res+=toTen(str);
        });
        System.out.println(toSNAFU(res));

    }


    @Override
    void processP2(Stream<String> s) {

    }
}
