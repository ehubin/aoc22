package aoc22;

import java.util.stream.Stream;

public class day10 extends b{
    int cycle=1,register=1,measure=20,res=0;
    boolean[] screen= new boolean[240];

    public static void main(String[] a) {
        day10 d=new day10();
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
            int addx=0;
            boolean noop=false;
            switch(str.charAt(0)) {
                case 'a': addx=Integer.parseInt(str.substring(5));break;
                case 'n': noop=true;break;
            }
            if(noop) {
                if(cycle==measure) {
                    res+= measure*register;
                    if(measure <=180 ) measure+=40;
                }
                cycle++;
            }
            else {
                if(cycle==measure||cycle+1==measure) {
                    res += measure * register;
                    if (measure <= 180) measure += 40;
                }
                cycle+=2;
                register+=addx;
            }
        });
        System.out.println(res);
    }


    @Override
    void processP2(Stream<String> s) {
        s.forEach(str -> {
            int addx=0;
            boolean noop=false;
            switch(str.charAt(0)) {
                case 'a': addx=Integer.parseInt(str.substring(5));break;
                case 'n': noop=true;break;
            }
            if(noop) {
                if(Math.abs(cycle%40-register-1) <=1) {
                    screen[cycle-1]=true;
                }
                cycle++;
            }
            else {
                if(Math.abs(cycle%40-register-1) <=1) screen[cycle-1]=true;
                if(Math.abs((cycle+1)%40+-register-1) <=1) screen[cycle]=true;
                cycle+=2;
                register+=addx;
            }
        });
        for(int i=0;i<6;++i) {
            for(int j=0;j<40;++j) {
                System.out.print(screen[40*i+j] ? "#":".");
            }
            System.out.println();
        }

    }
}
