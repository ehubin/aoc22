package aoc22;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

public class day22 extends b{
    boolean inmap=true;
    String path;
    public static void main(String[] a) {
        day22 d=new day22();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void processP1(Stream<String> s) {


        ArrayList<String> m=new ArrayList<>();
        s.forEach(str -> {
            if(inmap) {
                if(str.length()>0) m.add(str);
                else inmap=false;
            } else {
                path=str;
            }
        });
        Maze maze=new Maze(m);
        maze.walk(path,true);
        System.out.println(maze.score());

    }


    @Override
    void processP2(Stream<String> s) {
        ArrayList<String> m=new ArrayList<>();
        s.forEach(str -> {
            if(inmap) {
                if(str.length()>0) m.add(str);
                else inmap=false;
            } else {
                path=str;
            }
        });
        Maze maze=new Maze(m);
        maze.walk(path,false);
        System.out.println(maze.score());
    }
    enum Dir {up,right,down,left}

    static class Maze {
        char [][] map;
        int[] pos=new int[2];
        int faceLength;
        Dir dir= Dir.right;
        Maze(List<String> s) {
            String str;
            int max=0;
            for (String value : s) {
                if (value.length() > max) max = value.length();
            }
            faceLength=gcd(s.size(),max);
            System.out.println(faceLength);
            map=new char[s.size()+2][max+2];
            for(int i=0;i<s.size();++i) {
                str=s.get(i);
                for(int j=0;j<str.length();++j) map[i+1][j+1] = str.charAt(j)=='.' ? (char)1 :str.charAt(j)=='#' ? (char)2 : (char)0;
            }

            //find initial pos
            str=s.get(0);
            int col=0;
            while(str.charAt(col)!='.') ++col;
            pos[0]=col+1;pos[1]=1;

            //find where cube faces are
            map2= new int[s.size()/faceLength][max/faceLength];
            int faceNb=0;
            for(int i=0;i<map2.length;++i) for(int j=0;j<map2[0].length;++j) {
                if(map[i*faceLength+1][j*faceLength+1]==0) map2[i][j]=0;
                else {
                    map2[i][j]=faceNb;
                    invMap[faceNb][0]= j;
                    invMap[faceNb++][1]= i;
                }
            }

            //define mapping between faces
            map1[0]=refCube[0];
            Stack<Integer> Q=new Stack<>();
            Q.push(0);
            while(!Q.empty()) {
                int cur=Q.pop(),next;
                int[] coord=invMap[cur];
                if(coord[0]<map2[0].length-1 && (next=map2[coord[1]][coord[0]+1]) != -1) updateFace(next,cur,Q,1,3);
                if(coord[0]>0 && (next=map2[coord[1]][coord[0]-1]) != -1) updateFace(next,cur,Q,3,1);
                if(coord[1]<map2.length-1 && (next=map2[coord[1]+1][coord[0]]) != -1) updateFace(next,cur,Q,2,0);
                if(coord[1]>0 && (next=map2[coord[1]-1][coord[0]]) != -1) updateFace(next,cur,Q,0,2);
            }

        }
        static void updateFace(int next,int cur,Stack<Integer> Q,int dir,int Op) {
            if(map1[next][0]== 0 && map1[next][1]==0) {
                int newidx=map1[cur][dir];
                Q.push(newidx);
                map1[newidx]=refCube[newidx];
                if(newidx!= next) {
                    int[] coord1=invMap[next],coord2=invMap[newidx];
                    map2[coord1[1]][coord1[0]]=newidx;
                    map2[coord2[1]][coord2[0]]=next;
                    invMap[newidx]=coord1;
                    invMap[next]=coord2;
                }
                while(map1[newidx][Op] != cur) {
                    int tmp=map1[newidx][0];
                    map1[newidx][0]=map1[newidx][1];
                    map1[newidx][1]=map1[newidx][2];
                    map1[newidx][2]=map1[newidx][3];
                    map1[newidx][3]=tmp;
                }
            }
        }

        public void walk(String path,boolean p1) {
            int idx=0;
            do {
                if(Character.isDigit(path.charAt(idx))) {
                    int newidx=idx+1;
                    while(newidx<path.length()&&Character.isDigit(path.charAt(newidx))) ++newidx;
                    walk(Integer.parseInt(path.substring(idx,newidx)),p1);
                    idx=newidx;
                } else {
                    switch(path.charAt(idx)) {
                        case 'R':dir=dir==Dir.right ?Dir.down :dir==Dir.up?Dir.right :dir==Dir.left ?Dir.up :Dir.left; break;
                        case 'L':dir=dir==Dir.right ?Dir.up :dir==Dir.up?Dir.left :dir==Dir.left ?Dir.down :Dir.right; break;
                    }
                    ++idx;
                }
            } while(idx<path.length());
        }
        public void walk(int d,boolean p1) {
            switch(dir) {
                case right:
                    while(d>0) {
                        char next=map[pos[1]][pos[0]+1];
                        if(next==1) {++pos[0];--d;}
                        else if(next==2) return;
                        else if(next==0) {
                            if(p1) {
                                int tmp = pos[0];
                                while (map[pos[1]][tmp - 1] != 0) --tmp;
                                if (map[pos[1]][tmp] == 2) return;
                                else {
                                    pos[0] = tmp;
                                    --d;
                                }
                            } else {
                                getNext(d);
                                return;
                            }
                        }
                    }
                case left:
                    while(d>0) {
                        char next=map[pos[1]][pos[0]-1];
                        if(next==1) {--pos[0];--d;}
                        else if(next==2) return;
                        else if(next==0) {
                            if(!p1) {getNext(d); return;}
                            else {
                                int tmp = pos[0];
                                while (map[pos[1]][tmp + 1] != 0) ++tmp;
                                if (map[pos[1]][tmp] == 2) return;
                                else {
                                    pos[0] = tmp;
                                    --d;
                                }
                            }
                        }
                    }
                case up:
                    while(d>0) {
                        char next=map[pos[1]-1][pos[0]];
                        if(next==1) {--pos[1];--d;}
                        else if(next==2) return;
                        else if(next==0) {
                            if(!p1) {getNext(d);return;}
                            else {
                                int tmp = pos[1];
                                while (map[tmp + 1][pos[0]] != 0) ++tmp;
                                if (map[tmp][pos[0]] == 2) return;
                                else {
                                    pos[1] = tmp;
                                    --d;
                                }
                            }
                        }
                    }
                case down:
                    while(d>0) {
                        char next=map[pos[1]+1][pos[0]];
                        if(next==1) {++pos[1];--d;}
                        else if(next==2) return;
                        else if(next==0) {
                            if (!p1) {getNext(d);return;}
                            else {
                                int tmp = pos[1];
                                while (map[tmp - 1][pos[0]] != 0) --tmp;
                                if (map[tmp][pos[0]] == 2) return;
                                else {
                                    pos[1] = tmp;
                                    --d;
                                }
                            }
                        }
                    }
            }
        }
        static int[][] refCube={{4,2,1,5},{0,2,3,5},{4,3,1,0},{1,2,4,5},{5,3,2,0},{1,3,4,0}};
        static int[][] map1=new int[6][4];//= {{4,2,1,5},{0,2,3,5},{4,3,1,0},{1,2,4,5},{5,3,2,0},{1,3,4,0}};
        static int[][] map2;//= {{-1,0,2},{-1,1,-1},{5,3,-1},{4,-1,-1}};
        static int[][] invMap= new int[6][2];

        void getNext(int nbSteps) {
            int myIdx = map2[(pos[1]-1)/faceLength][(pos[0]-1)/faceLength];
            int edgeFrom=(dir==Dir.up||dir==Dir.down) ? (pos[0]-1)%faceLength :(pos[1]-1)%faceLength;
            int nextIdx= map1[myIdx][dir.ordinal()];
            int dirIdx=0;
            for(;dirIdx<4;++dirIdx) if(map1[nextIdx][dirIdx]==myIdx) break;
            Dir newFromDir=Dir.values()[dirIdx];
            Dir newDir=(newFromDir==Dir.up?Dir.down:newFromDir==Dir.down?Dir.up:newFromDir==Dir.right?Dir.left:Dir.right);
            int[] newFace=invMap[nextIdx];
            int newx = 1+faceLength*newFace[0];
            int newy = 1+faceLength*newFace[1];
            switch(newFromDir) {
                case up:
                    switch(dir) {
                        case down:
                        case left:
                            newx += edgeFrom;break;
                        case up:
                        case right:
                            newx+=faceLength-edgeFrom-1;break;
                    }
                    break;
                case down:
                    switch(dir) {
                        case down:
                        case left:
                            newx += faceLength-edgeFrom-1;break;
                        case up:
                        case right:
                            newx += edgeFrom;break;
                    }
                    newy+=faceLength-1;
                    break;
                case left:
                    switch(dir) {
                        case down:
                        case left:
                            newy += faceLength-edgeFrom-1;break;
                        case up:
                        case right:
                            newy += edgeFrom;break;
                    }
                    break;
                case right:
                    switch(dir) {
                        case up:
                        case right:
                            newy += faceLength-edgeFrom-1;break;
                        case down:
                        case left:
                            newy += edgeFrom;break;
                    }
                    newx+= faceLength-1;
                    break;
            }
            if(map[newy][newx] == 2) return;
            pos[0]=newx;pos[1]=newy;dir=newDir;
            walk(nbSteps-1,false);
        }

        public int score() {
            int res=0;
            switch (dir) {
                case up: res=3;break;
                case down: res=1;break;
                case left: res=2;break;
            }
            return res+1000*(pos[1])+4*(pos[0]);
        }

    }
    static public int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }






}
