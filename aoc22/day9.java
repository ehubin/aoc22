package aoc22;

import java.util.stream.Stream;

public class day9 extends b{
    final int S=650;
    int tx=S/2,ty=S/2,hx=S/2,hy=S/2;
    int[][] kt=new int[10][2];
    boolean[][] g=new boolean[S][S];

    public static void main(String[] a) {
        day9 d=new day9();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }
    void move() {
        if(Math.abs(tx-hx)>1) {tx+=hx>tx?1:-1; if(ty!=hy) ty=hy;}
        if(Math.abs(ty-hy)>1) {ty+=hy>ty?1:-1; if(tx!=hx) tx=hx;}
        g[tx][ty]=true;
    }
    void move2() {
        for(int i=1;i<10;++i) {
            if (Math.abs(kt[i][0] - kt[i - 1][0]) > 1) {
                kt[i][0] += kt[i - 1][0] > kt[i][0] ? 1 : -1;
                if (Math.abs(kt[i][1] - kt[i - 1][1])>=1) kt[i][1] += kt[i - 1][1] > kt[i][1] ? 1 : -1;

            }
            else if (Math.abs(kt[i][1] - kt[i - 1][1]) > 1) {
                kt[i][1] += kt[i - 1][1] > kt[i][1] ? 1 : -1;
                if (Math.abs(kt[i][0] - kt[i - 1][0])>=1) kt[i][0] += kt[i - 1][0] > kt[i][0] ? 1 : -1;
            }
        }
        g[kt[9][0]][kt[9][1]]=true;
    }
    void print() {
        int res=0;
        for(boolean[]b : g) {
            for (boolean c : b) {
                if (c) {System.out.print("#");++res;}
                else System.out.print(".");
            }
            System.out.println();
        }
        System.out.println(res);
    }

    @Override
    void processP1(Stream<String> s) {
        g[S/2][S/2]=true;
        s.forEach(str -> {
            int dx=0,dy=0;
            switch(str.charAt(0)) {
                case 'R': dy=1;break;
                case 'L': dy=-1;break;
                case 'U': dx=-1;break;
                case 'D': dx=1;break;
            }
            int d=Integer.parseInt(str.substring(2));
            for(int i=0;i<d;++i) {
                hx+=dx;hy+=dy;
                move();
            }
        });
        print();
    }


    @Override
    void processP2(Stream<String> s) {
        g[S/2][S/2]=true;
        for(int[] k:kt) {k[0]=S/2;k[1]=S/2;}
        s.forEach(str -> {
            int dx=0,dy=0;
            switch(str.charAt(0)) {
                case 'R': dy=1;break;
                case 'L': dy=-1;break;
                case 'U': dx=-1;break;
                case 'D': dx=1;break;
            }
            int d=Integer.parseInt(str.substring(2));
            for(int i=0;i<d;++i) {
                kt[0][0]+=dx;kt[0][1]+=dy;
                move2();
            }
        });
        print();


    }
}
