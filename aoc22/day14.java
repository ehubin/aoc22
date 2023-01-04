package aoc22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class day14 extends b{
    int res;
    ArrayList<ArrayList<int[]>> list=new ArrayList<>();


    public static void main(String[] a) {
        day14 d=new day14();
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
            ArrayList<int[]> cur=new ArrayList<>();
            for(String s1:str.split(" -> ")) {
                String[] s2=s1.split(",");
                cur.add(new int[] {Integer.parseInt(s2[0]),Integer.parseInt(s2[1])});
            }
            list.add(cur);
        });
        int minx=Integer.MAX_VALUE,miny=Integer.MAX_VALUE,maxx=Integer.MIN_VALUE,maxy=Integer.MIN_VALUE;
        for(ArrayList<int[]> ai:list) {
            for(int[] ti:ai) {
                if(ti[0] < minx) minx=ti[0];
                if(ti[1] < miny) miny=ti[1];
                if(ti[0] > maxx) maxx=ti[0];
                if(ti[1] > maxy) maxy=ti[1];
                System.out.print( ti[0]+","+ti[1]+"  ");
            }
            System.out.println();
        }
        System.out.println(minx+","+maxx+" - "+miny+","+maxy);
        int[][] cave=new int[maxx+2][maxy+2];
        for(ArrayList<int[]> ai:list) {
            for(int i=0;i<ai.size()-1;++i) {
                int[] p1=ai.get(i),p2=ai.get(i+1);
                if(p1[0]==p2[0]) {
                    int py1=Math.min(p1[1],p2[1]),py2=Math.max(p1[1],p2[1]);
                    for(int j=py1;j<=py2;++j) cave[p1[0]][j]=1;
                }
                if(p1[1]==p2[1]) {
                    int py1=Math.min(p1[0],p2[0]),py2=Math.max(p1[0],p2[0]);
                    for(int j=py1;j<=py2;++j) cave[j][p1[1]]=1;
                }
            }
        }


        int count=0;
        while(addGrain(cave,maxy)<= maxy ) ++count;
        System.out.println(count);

    }

    int addGrain(int[][] cave,int max) {
        int x=500,y=0;
        while(y<=max) {
            if(cave[x][y+1]==0) y+=1;
            else if(cave[x-1][y+1]==0) {x-=1; y+=1; }
            else if(cave[x+1][y+1]==0) {x+=1; y+=1; }
            else { cave[x][y]=2; break;}
        }
        System.out.println(x+","+y);
        return y;
    }

    @Override
    void processP2(Stream<String> s) {
        s.forEach(str->{
            ArrayList<int[]> cur=new ArrayList<>();
            for(String s1:str.split(" -> ")) {
                String[] s2=s1.split(",");
                cur.add(new int[] {Integer.parseInt(s2[0]),Integer.parseInt(s2[1])});
            }
            list.add(cur);
        });
        int minx=Integer.MAX_VALUE,miny=Integer.MAX_VALUE,maxx=Integer.MIN_VALUE,maxy=Integer.MIN_VALUE;
        for(ArrayList<int[]> ai:list) {
            for(int[] ti:ai) {
                if(ti[0] < minx) minx=ti[0];
                if(ti[1] < miny) miny=ti[1];
                if(ti[0] > maxx) maxx=ti[0];
                if(ti[1] > maxy) maxy=ti[1];
                System.out.print( ti[0]+","+ti[1]+"  ");
            }
            System.out.println();
        }
        System.out.println(minx+","+maxx+" - "+miny+","+maxy);
        int[][] cave=new int[maxx*2][maxy+3];
        for(ArrayList<int[]> ai:list) {
            for(int i=0;i<ai.size()-1;++i) {
                int[] p1=ai.get(i),p2=ai.get(i+1);
                if(p1[0]==p2[0]) {
                    int py1=Math.min(p1[1],p2[1]),py2=Math.max(p1[1],p2[1]);
                    for(int j=py1;j<=py2;++j) cave[p1[0]][j]=1;
                }
                if(p1[1]==p2[1]) {
                    int py1=Math.min(p1[0],p2[0]),py2=Math.max(p1[0],p2[0]);
                    for(int j=py1;j<=py2;++j) cave[j][p1[1]]=1;
                }
            }
        }
        for(int i=0;i<maxx*2;++i) cave[i][maxy+2]=1;


        int y,count=0;
        while((y=addGrain(cave,maxy+2))!=0 ) {++count;}
        System.out.println(count+1);
    }



}
