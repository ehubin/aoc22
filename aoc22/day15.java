package aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class day15 extends b{
    int res;
    ArrayList<int[]> list=new ArrayList<>();
    HashSet<Pt> beacons = new HashSet<>();

    public static void main(String[] a) {
        day15 d=new day15();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void processP1(Stream<String> s) {
        s.forEach(str->{
            int[] cur=new int[4];
            int i=str.indexOf('='),e=i+1;
            if(str.charAt(e)=='-') ++e;
            while(Character.isDigit(str.charAt(e++)));
            cur[0]=Integer.parseInt(str.substring(i+1,e-1));
            i=str.indexOf('=',e);e=i+1;
            if(str.charAt(e)=='-') ++e;
            while(Character.isDigit(str.charAt(e++)));
            cur[1]=Integer.parseInt(str.substring(i+1,e-1));
            i=str.indexOf('=',e);e=i+1;
            if(str.charAt(e)=='-') ++e;
            while(Character.isDigit(str.charAt(e++)));
            cur[2]=Integer.parseInt(str.substring(i+1,e-1));
            i=str.indexOf('=',e);e=i+1;
            cur[3]=Integer.parseInt(str.substring(i+1,str.length()));
            list.add(cur);
            beacons.add(new Pt(cur[2],cur[3]));
        });
        //for(Pt p:beacons) System.out.println(p.x+","+p.y);
        //for(int[] ai:list) System.out.println(Arrays.toString(ai));
        ArrayList<int[]> over=new ArrayList<>();
        final int ROW=2000000;
        for(int[] ai:list) merge(over,getRange(ai[0],ai[1],ai[2],ai[3],ROW));
        int res=0;
        for(int[] r:over) {
            for(Pt p:beacons) if(p.y==ROW && p.x>=r[0] && p.x<=r[1]) --res;
            res+=r[1]-r[0]+1;
        }
        System.out.println(res);
    }

    int[] getRange(int sx,int sy, int bx,int by,int row) {
        int dist=Math.abs(bx-sx)+Math.abs(by-sy),d2;
        if((d2=Math.abs(row-sy))<=dist) {
            return new int[] {sx+d2-dist,sx-d2+dist};
        }
        return new int[] {};
    }

    void merge(ArrayList<int[]> r,int[] n) {
        if(n==null||n.length != 2) return;
        //System.out.println(Arrays.toString(n));
        ArrayList<Integer> rem=new ArrayList<>();
        for(int i=0;i<r.size() ;++i) {
            if(!(r.get(i)[1]<n[0]) && !(r.get(i)[0]>n[1])) rem.add(i);
        }
        if(rem.size()==0) r.add(n);
        else {
            int[] e=new int[] {n[0],n[1]};
            for(int i:rem) {
                if(r.get(i)[0]<e[0]) e[0]=r.get(i)[0];
                if(r.get(i)[1]>e[1]) e[1]=r.get(i)[1];
            }
            for(int x = rem.size() - 1; x >= 0; x--)
            {
                r.remove(rem.get(x).intValue());
            }
            r.add(e);
        }
    }
    @Override
    void processP2(Stream<String> s) {
        s.forEach(str->{
            int[] cur=new int[4];
            int i=str.indexOf('='),e=i+1;
            if(str.charAt(e)=='-') ++e;
            while(Character.isDigit(str.charAt(e++)));
            cur[0]=Integer.parseInt(str.substring(i+1,e-1));
            i=str.indexOf('=',e);e=i+1;
            if(str.charAt(e)=='-') ++e;
            while(Character.isDigit(str.charAt(e++)));
            cur[1]=Integer.parseInt(str.substring(i+1,e-1));
            i=str.indexOf('=',e);e=i+1;
            if(str.charAt(e)=='-') ++e;
            while(Character.isDigit(str.charAt(e++)));
            cur[2]=Integer.parseInt(str.substring(i+1,e-1));
            i=str.indexOf('=',e);e=i+1;
            cur[3]=Integer.parseInt(str.substring(i+1,str.length()));
            list.add(cur);
            beacons.add(new Pt(cur[2],cur[3]));
        });
        //for(Pt p:beacons) System.out.println(p.x+","+p.y);
        //for(int[] ai:list) System.out.println(Arrays.toString(ai));
        final int ROW=4000000;
        for(int i=0;i<=ROW;++i) {
            ArrayList<int[]> over = new ArrayList<>();
            for (int[] ai : list) merge(over, getRange(ai[0], ai[1], ai[2], ai[3], i));
            if (over.size() == 2) {
                System.out.println(over.get(0)[1] + 1 + "," + i);
                System.out.println(4000000L*(over.get(0)[1] + 1)+i);
                break;
            }
        }
    }

    static class Pt {
        Pt(int x,int y ) {this.x=x;this.y=y;}
        int x,y;
        @Override public boolean equals(Object o) {
            return o instanceof Pt ? ((Pt)o).x==x && ((Pt)o).y==y:false;
        }
        @Override public int hashCode() { return x+y<<16;}
    }

}
