package aoc22;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class day4 extends b{
    int res=0;
    public static void main(String[] a) {
        day4 d=new day4();
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

        Pattern p= Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
        int score=s.mapToInt(str -> {
            Matcher m=p.matcher(str);
            if(m.find()) {
                int p1 = Integer.parseInt(m.group(1));
                int p2 = Integer.parseInt(m.group(2));
                int p3 = Integer.parseInt(m.group(3));
                int p4 = Integer.parseInt(m.group(4));
                if ((p1 <= p3 && p2 >= p4)||(p1>=p3 && p2<=p4 )) return 1;
            }
            return 0;
        }).sum();
        System.out.println(score);
    }


    @Override
    void processP2(Stream<String> s) {
        Pattern p= Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
        int score=s.mapToInt(str -> {
            Matcher m=p.matcher(str);
            if(m.find()) {
                int p1 = Integer.parseInt(m.group(1));
                int p2 = Integer.parseInt(m.group(2));
                int p3 = Integer.parseInt(m.group(3));
                int p4 = Integer.parseInt(m.group(4));
                if ((p1 <= p4 && p2 >= p3)||(p3<=p2 && p4>=p1 )) return 1;
            }
            return 0;
        }).sum();
        System.out.println(score);
    }
}
