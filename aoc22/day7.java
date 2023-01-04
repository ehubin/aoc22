package aoc22;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

public class day7 extends b{
    static class Dir {
        String name;
        long size=0L;
        Dir parent;
        ArrayList<Dir> subDirs=new ArrayList<>();
        private Dir(String n,Dir p) {
            name=n;
            parent = p;
        }
        long getSize() {
            long r=size;
            for(Dir d:subDirs) r+=d.getSize();
            return r;
        }
        static Dir newDir(String s,Dir p) {
            Dir d=new Dir(s,p);
            if(p!= null) p.subDirs.add(d);
            instances.add(d);
            return d;
        }
        public String toString() {
            return toString("");
        }
        public String toString(String pre) {
            StringBuffer sb = new StringBuffer(pre+">>"+name +" "+getSize()+ "-"+size);
            for(Dir d:subDirs) sb.append("\n"+d.toString("\t"+pre));
            return sb.toString();
        }
        static ArrayList<Dir> instances=new ArrayList<>();
    }

    int res=0;
    Dir cur=null;

    public static void main(String[] a) {
        day7 d=new day7();
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
            //System.out.println(str);
            if (str.startsWith("$ ls")||str.startsWith("dir")) return;
            if(str.startsWith("$ cd ..")) {
                cur = cur.parent;
                return;
            } else if(str.startsWith("$ cd")) {
                //System.out.println(str.substring(5));
                cur = Dir.newDir(str.substring(5),cur);
                return;
            } else {
                //System.out.println("<"+str+">");
                int size = Integer.parseInt(str.substring(0,str.indexOf(' ')));
                cur.size+=size;
                //System.out.println(size);
            }
        });
        for(Dir d:Dir.instances) if(d.getSize()<=100000) {
            System.out.println(d+"\n====================");
            res+= d.getSize();
        }
        System.out.println(res);
    }


    @Override
    void processP2(Stream<String> s) {

        s.forEach(str -> {
            //System.out.println(str);
            if (str.startsWith("$ ls")||str.startsWith("dir")) return;
            if(str.startsWith("$ cd ..")) {
                cur = cur.parent;
                return;
            } else if(str.startsWith("$ cd")) {
                //System.out.println(str.substring(5));
                cur = Dir.newDir(str.substring(5),cur);
                return;
            } else {
                //System.out.println("<"+str+">");
                int size = Integer.parseInt(str.substring(0,str.indexOf(' ')));
                cur.size+=size;
                //System.out.println(size);
            }
        });
        Dir root =Dir.instances.get(0);
        long toFree=root.getSize()-40000000L;
        System.out.println(root.name+":"+toFree+"\t"+root.getSize());
        Dir.instances.sort(Comparator.comparing(Dir::getSize));
        for(Dir d:Dir.instances) if(d.getSize() >=toFree) {
            System.out.println("======>"+d.getSize());
            break;
        }
    }
}
