package aoc22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class day24 extends b{
    static int w,h,cycle;
    char[][] map;
    static boolean[][][] tMap;
    public static void main(String[] a) {
        day24 d=new day24();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void processP1(Stream<String> s) {
        ArrayList<String> ls=new ArrayList<>();
        s.forEach(ls::add);

        w=ls.get(0).length()-2;
        h=ls.size()-2;
        cycle=lcm(h,w);
        tMap=new boolean[cycle][h][w];
        System.out.println("Cycle="+cycle);
        map=new char[h][w];
        for(int i=0;i<h;++i) {
            System.arraycopy(ls.get(i+1).toCharArray(),1,map[i],0,map[i].length);
        }

        for(int t=0;t<cycle;t++) {
            for(int y=0;y<h;++y) {
                for(int x=0;x<w;++x) {
                    switch(map[y][x]) {
                        case '>':  tMap[t][y][(x+t)%w]=true;break;
                        case '<':  tMap[t][y][(x+100*w-t)%w]=true;break;
                        case '^':  tMap[t][(y+100*h-t)%h][x]=true;break;
                        case 'v':  tMap[t][(y+t)%h][x]=true;break;
                    }
                }
            }
        }
/*
        for(int t=0;t<cycle;t++) {
            System.out.println(t);
            for (int y = 0; y < h; ++y) {
                for (int x = 0; x < w; ++x) {
                    System.out.print(tMap[t][y][x] ? "#":".");
                }
                System.out.println();
            }
            System.out.println();
        }
*/

        Dijkstra<Node24,Integer> D=new Dijkstra<>();
        D.dijkstra(Node24.getStart(0));
        System.out.println(Node24.END.dist);
    }


    @Override
    void processP2(Stream<String> s) {
        ArrayList<String> ls=new ArrayList<>();
        s.forEach(ls::add);

        w=ls.get(0).length()-2;
        h=ls.size()-2;
        cycle=lcm(h,w);
        tMap=new boolean[cycle][h][w];
        System.out.println("Cycle="+cycle);
        map=new char[h][w];
        for(int i=0;i<h;++i) {
            System.arraycopy(ls.get(i+1).toCharArray(),1,map[i],0,map[i].length);
        }

        for(int t=0;t<cycle;t++) {
            for(int y=0;y<h;++y) {
                for(int x=0;x<w;++x) {
                    switch(map[y][x]) {
                        case '>':  tMap[t][y][(x+t)%w]=true;break;
                        case '<':  tMap[t][y][(x+100*w-t)%w]=true;break;
                        case '^':  tMap[t][(y+100*h-t)%h][x]=true;break;
                        case 'v':  tMap[t][(y+t)%h][x]=true;break;
                    }
                }
            }
        }


        Dijkstra<Node24,Integer> D=new Dijkstra<>();
        D.dijkstra(Node24.getStart(0));
        int res=Node24.END.dist;
        System.out.println(Node24.END.dist);
        Node24.nodes.clear();Node24.START.dist=Integer.MAX_VALUE;
        D.dijkstra(Node24.getStartEnd(res));
        res+=Node24.START.dist;
        System.out.println(Node24.START.dist);
        Node24.nodes.clear();Node24.END.dist=Integer.MAX_VALUE;
        D.dijkstra(Node24.getStart(res));
        System.out.println(Node24.END.dist);
        res+=Node24.END.dist;
        System.out.println(res);
    }
    static class Node24 extends Node<Integer,Node24> {
        static HashMap<Integer,Node24> nodes= new HashMap<>();
        int x,y,t;
        static Node24 getStart(int t) {
            int id=getId(w-1,h,t);
            Node24 n=nodes.get(id);
            if(n==null) {
                n = new Node24(0, -1, t) {
                    @Override
                    List<Node24> adj() {
                        int rt=(t+1)%cycle;
                        ArrayList<Node24> res = new ArrayList<>();
                        res.add(getStart(rt));
                        if (!tMap[rt][0][0]) res.add(getNode(0, 0, rt));
                        return res;
                    }
                };
            }
            return n;
        }
        static Node24 getStartEnd(int t) {
            int id=getId(w-1,h,t);
            Node24 n=nodes.get(id);
            if(n==null) {
                n = new Node24(w - 1, h, t) {
                    @Override
                    List<Node24> adj() {
                        ArrayList<Node24> res = new ArrayList<>();
                        int rt=(t+1)%cycle;
                        res.add(getStartEnd(rt));
                        if (!tMap[rt][h - 1][w - 1]) res.add(getNode(w - 1, h - 1, rt));
                        return res;
                    }
                };
            }
            return n;
        }
        static Node24 END= new Node24(w-1,h,-1) {
            @Override
            List<Node24> adj() {
                return new ArrayList<>();
            }
        };
        static Node24 START= new Node24(0,-1,-1) {
            @Override
            List<Node24> adj() {
                return new ArrayList<>();
            }
        };
        private Node24(int x,int y,int t) {
            super(getId(x,y,t), 1);
            this.x=x;this.y=y;this.t=t;
        }
        static Node24 getNode(int x,int y,int t) {
            int id=getId(x,y,t);
            Node24 n=nodes.get(id);
            if(n==null) {
                n=new Node24(x,y,t);
                nodes.put(id,n);
            }
            return n;
        }
        static int getId(int x,int y,int t) { return x+w*y+(t<<16);}

        @Override
        List<Node24> adj() {
            ArrayList<Node24> res=new ArrayList<>();
            if(x==w-1&&y==h-1) {res.add(END);}
            if(x==0&&y==0) {res.add(START); }
            int rt=(t+1)%cycle;
            if(!tMap[rt][y][x]) res.add(getNode(x,y,rt));
            if(x+1<w && !tMap[rt][y][x+1]) res.add(getNode(x+1,y,rt));
            if(x-1>=0 && !tMap[rt][y][x-1]) res.add(getNode(x-1,y,rt));
            if(y-1>=0 && !tMap[rt][y-1][x]) res.add(getNode(x,y-1,rt));
            if(y+1<h && !tMap[rt][y+1][x]) res.add(getNode(x,y+1,rt));
            return res;
        }
        @Override public String toString() { return t+":"+x+","+y+" - "+dist;}
    }
    static public int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }
    static public int lcm(int a, int b) {
        return a*b/gcd(a,b);
    }
}
