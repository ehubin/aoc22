package aoc22;

import com.iwebpp.crypto.TweetNaclFast;
import org.jgrapht.alg.util.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day18 extends b{
    int res=0;
    ArrayList<ArrayList<Cube>> groups=new ArrayList<>();


    public static void main(String[] a) {
        day18 d=new day18();
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
            String[] coord=str.split(",");
            Cube c=new Cube(Integer.parseInt(coord[0]),Integer.parseInt(coord[1]),Integer.parseInt(coord[2]));
            ArrayList<Integer> touches=new ArrayList<>();
            for(int i=0;i<groups.size();++i) {
                for(Cube cu:groups.get(i)) if(c.touches(cu)) {touches.add(i);break;}
            }

            if(touches.size()==0) {
                ArrayList<Cube> n=new ArrayList<>();
                n.add(c);
                groups.add(n);
            } else if(touches.size() ==1){
                groups.get(touches.get(0)).add(c);
            } else {
                //System.out.println(Arrays.toString(touches.toArray()));
                ArrayList<Cube> n=new ArrayList<>();
                n.add(c);
                for(Integer i:touches) n.addAll(groups.get(i));
                for(int j=touches.size()-1;j>=0;--j) {
                    groups.remove((int)(touches.get(j)));
                }
                groups.add(n);
            }
        });

        for(ArrayList<Cube> gr:groups) {
            for (Cube c : gr) System.out.print(c + " - ");
            System.out.println();
        }
        for(ArrayList<Cube> gr:groups) res+= faceCount(gr);
        System.out.println(res);

    }



    @Override
    void processP2(Stream<String> s) {
        List<Cube> cubes=s.map(str->{
            String[] coord=str.split(",");
            return new Cube(Integer.parseInt(coord[0]),Integer.parseInt(coord[1]),Integer.parseInt(coord[2]));
        }).collect(Collectors.toList());
        Group theGroup=new Group(cubes);
        System.out.println(theGroup.extFaceCount());
    }



    static class Cube {
        int x,y,z;
        Cube(int x,int y,int z) {this.x=x;this.y=y;this.z=z;}
        Cube(Cube o) {this.x=o.x;this.y=o.y;this.z=o.z;}
        @Override public String toString() { return x+","+y+","+z;}
        boolean touches(Cube o) {
            return ((x-o.x)*(x-o.x)+(y-o.y)*(y-o.y)+(z-o.z)*(z-o.z))==1;
        }
        @Override public boolean equals(Object o) {
            if(o instanceof  Cube) {
                Cube c=  ((Cube)o);
                return x==c.x && y==c.y && z==c.z;
            }
            return false;
        }
        @Override public int hashCode() {
            return x+y<<11+z<<22;
        }

        public Cube toward(Group.Direction d) {
            Cube res=new Cube(this);
            switch(d) {
                case xPlus: res.x+=1;break;
                case xMinus: res.x-=1;break;
                case yPlus: res.y+=1;break;
                case yMinus: res.y-=1;break;
                case zPlus: res.z+=1;break;
                case zMinus: res.z-=1;break;
            }
            return res;
        }
    }
    int faceCount(ArrayList<Cube> a) {
        int res=0;
        for(Cube c:a) {
            int adj=0;
            for(Cube oc:a) {
                if(c.touches(oc)) ++adj;
            }
            res+=6-adj;
        }
        return res;
    }

    static class Group {
        enum Direction { xPlus,xMinus,yPlus,yMinus,zPlus,zMinus}
        List<Cube> list;
        HashSet<Cube> set=new HashSet<>();
        int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, minz = Integer.MAX_VALUE;
        int maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE, maxz = Integer.MIN_VALUE;
        Group(List<Cube> a) {
            list=a;
            for (Cube c : a) {
                set.add(c);
                if (c.x < minx) minx = c.x;
                if (c.y < miny) miny = c.y;
                if (c.z < minz) minz = c.z;
                if (c.x > maxx) maxx = c.x;
                if (c.y > maxy) maxy = c.y;
                if (c.z > maxz) maxz = c.z;
            }
        }
        List<Direction> getFreeFaces(Cube c) {
            ArrayList<Direction> res=new ArrayList<>();
            Cube tmp = new Cube (c.x,c.y,c.z);
            tmp.x+=1; if(!set.contains(tmp)) res.add(Direction.xPlus);
            tmp.x-=2; if(!set.contains(tmp)) res.add(Direction.xMinus);
            tmp.x+=1;tmp.y+=1; if(!set.contains(tmp)) res.add(Direction.yPlus);
            tmp.y-=2; if(!set.contains(tmp)) res.add(Direction.yMinus);
            tmp.y+=1;tmp.z+=1; if(!set.contains(tmp)) res.add(Direction.zPlus);
            tmp.z-=2; if(!set.contains(tmp)) res.add(Direction.zMinus);
            return res;
        }
        List<Direction> getFreeFacesInCube(Cube c) {
            ArrayList<Direction> res=new ArrayList<>();
            Cube tmp = new Cube (c.x,c.y,c.z);
            tmp.x+=1; if(!set.contains(tmp)&&tmp.x<=maxx+1) res.add(Direction.xPlus);
            tmp.x-=2; if(!set.contains(tmp)&&tmp.x>=minx-1) res.add(Direction.xMinus);
            tmp.x+=1;tmp.y+=1; if(!set.contains(tmp)&&tmp.y<=maxy+1) res.add(Direction.yPlus);
            tmp.y-=2; if(!set.contains(tmp)&&tmp.y>=miny-1) res.add(Direction.yMinus);
            tmp.y+=1;tmp.z+=1; if(!set.contains(tmp)&&tmp.z<=maxz+1) res.add(Direction.zPlus);
            tmp.z-=2; if(!set.contains(tmp)&&tmp.z>=minz-1) res.add(Direction.zMinus);
            return res;
        }

        int extFaceCount() {
            HashSet<Cube> externals=findExternal(new Cube(maxx+1,maxy+1,maxz+1));
            int res=0;
            for(Cube c:list) {
                for(Direction d:getFreeFaces(c)) {
                    if(externals.contains(c.toward(d))) ++res;
                }
            }
            return res;
        }
        //bfs search for external
        HashSet<Cube> findExternal(Cube in) {
            Stack<Cube> Q=new Stack<>();
            HashSet<Cube> visited=new HashSet<>();
            Q.add(in);
            while(!Q.isEmpty()) {
                Cube c=Q.pop();
                visited.add(c);
                for(Direction dir:getFreeFacesInCube(c)) {
                    Cube next=c.toward(dir);
                    if(!visited.contains(next)) {
                        Q.push(next);
                    }
                }
            }
            return visited;
        }
    }
}
