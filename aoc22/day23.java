package aoc22;

import java.util.*;
import java.util.stream.Stream;

public class day23 extends b{
    static HashSet<Elf> all=new HashSet<>();
    static HashMap<Elf,Elf>allOrders=new HashMap<>();
    int parseIdx=0;
    public static void main(String[] a) {
        day23 d=new day23();
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
            for(int i=0;i<str.length();++i) if(str.charAt(i)=='#') Elf.getElf(i,parseIdx);
            ++parseIdx;
        });
        for(int round=0;round<10;++round) {
            allOrders.clear();
            for (Elf elf : all) elf.plan(round);
            //Elf.print(); System.out.println();
            Elf.moveAll();
            Elf.print();System.out.println();
        }
        System.out.println(Elf.area()-all.size());
    }


    @Override
    void processP2(Stream<String> s) {
        s.forEach(str -> {
            for(int i=0;i<str.length();++i) if(str.charAt(i)=='#') Elf.getElf(i,parseIdx);
            ++parseIdx;
        });
        int round=0;
        while(true) {
            allOrders.clear();
            for (Elf elf : all) elf.plan(round);
            ++round;
            if(Elf.moveAll()==0) {
                Elf.print();System.out.println();
                System.out.println(round);
                return;
            }
            //Elf.print();System.out.println();
        }
    }
    static class Elf {
        int x,y;
        static final Elf NOELF=new Elf(Integer.MIN_VALUE,Integer.MIN_VALUE);
        Elf(int x,int y) {this.x=x;this.y=y;}
        static void getElf(int x,int y) { all.add(new Elf(x,y)); }
        void plan(int round) {
            int occupied=0;
            for(int i=0;i<allDir.length;++i)
                if(hasElf(x+allDir[i][0],y+allDir[i][1]))
                    occupied |= 1<<i;
            if(occupied==0) return;
            for(int i=0;i<4;++i) {
                int option=(round+i) % 4,dirMask=directions[option];
                if ((occupied&dirMask) != 0) continue;
                int[] dir = theDir[option];
                Elf target = new Elf(x + dir[0], y + dir[1]);
                Elf existing = allOrders.get(target);
                if (existing != null && existing != NOELF) allOrders.put(target, NOELF);
                if (existing == null) allOrders.put(target, this);
                return;
            }
        }
        static int moveAll() {
            int res=0;
            for(Map.Entry<Elf,Elf> en:allOrders.entrySet())
                if(en.getValue()!= NOELF) {++res;all.remove(en.getValue());all.add(en.getKey());}
            return res;
        }

        static boolean hasElf(int x,int y) { return all.contains(new Elf(x,y));}

        static int[][] allDir= {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
        static int[] directions ={7,7<<4,7<<6,7<<2};
        static int[][] theDir={{0,-1},{0,1},{-1,0},{1,0}};

        static int area() {
            int minx=Integer.MAX_VALUE,miny=Integer.MAX_VALUE,maxx=Integer.MIN_VALUE,maxy=Integer.MIN_VALUE;
            for (Elf e : all) {
                if (e.x > maxx) maxx = e.x;
                if (e.x < minx) minx = e.x;
                if (e.y > maxy) maxy = e.y;
                if (e.y < miny) miny = e.y;
            }
            return (1+maxx-minx)*(1+maxy-miny);

        }
        static void print() {
            int minx=Integer.MAX_VALUE,miny=Integer.MAX_VALUE,maxx=Integer.MIN_VALUE,maxy=Integer.MIN_VALUE;
            for (Elf e : all) {
                if (e.x > maxx) maxx = e.x;
                if (e.x < minx) minx = e.x;
                if (e.y > maxy) maxy = e.y;
                if (e.y < miny) miny = e.y;
            }
            miny-=1;minx-=1;maxx+=1;maxy+=1;
            for(int y= miny;y<=maxy;++y) {
                for(int x=minx;x<=maxx;++x) {
                    Elf cur=new Elf(x,y);
                    System.out.print(all.contains(cur)? '#':(allOrders.containsKey(cur)? orderChar(cur,allOrders.get(cur)):'.'));
                }
                System.out.println();
            }

        }
        static char orderChar(Elf target,Elf from) {
            if(from==NOELF) return '*';
            if(from.x==target.x+1) return '<';
            if(from.x==target.x-1) return '>';
            if(from.y==target.y+1) return '^';
            if(from.y==target.y-1) return 'v';
            return '@';
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Elf elf = (Elf) o;
            return x == elf.x && y == elf.y;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
