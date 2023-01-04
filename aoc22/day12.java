package aoc22;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class day12 extends b{
    int idx=0;
    int[] start=new int[2],end=new int[2];
    ArrayList<char[]> maze=new ArrayList<>();
    MyNode[][] nodes;
    BiFunction<Character,Character,Boolean> check = (Character from, Character to)->from+1>=to;

    public static void main(String[] a) {
        day12 d=new day12();
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
            int i;
            if((i=str.indexOf('S')) != -1) {start[0]=idx;start[1]=i;str=str.replace('S','a');}
            if((i=str.indexOf('E')) != -1) {end[0]=idx;end[1]=i;str=str.replace('E','z');}
            System.out.println(str);
            maze.add(str.toCharArray());
            ++idx;

        });
        nodes=new MyNode[maze.size()][maze.get(0).length];
        for(int i=0;i<nodes.length;++i) for(int j=0;j<nodes[0].length;++j) nodes[i][j] = new MyNode(i,j);
        System.out.println("Start: "+Arrays.toString(start));
        System.out.println("End: "+Arrays.toString(end));
        Dijkstra d=new Dijkstra();
        d.dijkstra(nodes[start[0]][start[1]]);
        System.out.println(d.get(new MyNode(end[0],end[1]).id).dist);
    }



    @Override
    void processP2(Stream<String> s) {
        check=(from,to)->to+1>=from;
        s.forEach(str->{
            int i;
            if((i=str.indexOf('S')) != -1) {start[0]=idx;start[1]=i;str=str.replace('S','a');}
            if((i=str.indexOf('E')) != -1) {end[0]=idx;end[1]=i;str=str.replace('E','z');}
            System.out.println(str);
            maze.add(str.toCharArray());
            ++idx;

        });
        nodes=new MyNode[maze.size()][maze.get(0).length];
        for(int i=0;i<nodes.length;++i) for(int j=0;j<nodes[0].length;++j) nodes[i][j] = new MyNode(i,j);
        System.out.println("Start: "+Arrays.toString(start));
        System.out.println("End: "+Arrays.toString(end));
        Dijkstra<MyNode,Integer> d=new Dijkstra<>();
        d.dijkstra(nodes[end[0]][end[1]]);
        int res=Integer.MAX_VALUE;
        Optional<MyNode> theOne=d.getAll().min((MyNode n1, MyNode n2)-> {
            if(maze.get(n1.x)[n1.y] == 'a') {
                if(maze.get(n2.x)[n2.y] != 'a') return -1;
                else return Integer.compare(n1.dist,n2.dist);
            }
            else if(maze.get(n2.x)[n2.y] == 'a') {
                if (maze.get(n1.x)[n1.y] != 'a') return 1;
                else return Integer.compare(n1.dist, n2.dist);
            }
            else { return 0;} // both different from a
        });
        System.out.println(theOne.get().dist);

    }

    class MyNode extends Node<Integer,MyNode>{
        int x,y;
        MyNode(int x,int y) {
            super(x+(y<<16),1);
            this.x=x;this.y=y;
        }
        boolean canGo(char me,int x1,int y1) {
            if(x1>=0 && x1 <maze.size() && y1 >=0 && y1 < maze.get(0).length) {
                char to=maze.get(x1)[y1];
                return check.apply(me, to);
            }
            return false;
        }
        @Override
        public String toString() {
            return "x=" + x +", y=" + y +" -- "+id+" -> "+dist+" * "+maze.get(x)[y];
        }

        @Override
        List<MyNode> adj() {
            char me=maze.get(x)[y];


            ArrayList<MyNode> res=new ArrayList<>();
            if(canGo(me,x-1,y)) res.add(nodes[x-1][y]);
            if(canGo(me,x,y-1)) res.add(nodes[x][y-1]);
            if(canGo(me,x+1,y))  res.add(nodes[x+1][y]);
            if(canGo(me,x,y+1)) res.add(nodes[x][y+1]);
            return res;
        }
    }

}
