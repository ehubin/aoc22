package aoc22;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class day16 extends b{
    FloydW fw;
    int best=2500;
    HashMap<State2,Integer> known=new HashMap<>();
    public static void main(String[] a) {
        day16 d=new day16();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void processP1(Stream<String> s) {
        Pattern pat=Pattern.compile("Valve (\\w\\w) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");
        ArrayList<Integer> nonZeroFlow=new ArrayList<>();
        s.forEach(str->{
            Matcher m=pat.matcher(str);

            if(m.find()) {
                Vertex v=Vertex.getVertex(m.group(1), Integer.parseInt(m.group(2)), m.group(3).split(", "));
                if(v.flow>0) nonZeroFlow.add(v.idx);
            }
        });
        fw=new FloydW(Vertex.list.toArray(new Vertex[0]));
        Vertex cur=Vertex.all.get("AA");
        State init=new State(cur.idx,0,nonZeroFlow);
        System.out.println(init.eval());
    }


    @Override
    void processP2(Stream<String> s) {
        Pattern pat=Pattern.compile("Valve (\\w\\w) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");
        ArrayList<Vertex> nonZeroFlow=new ArrayList<>();
        s.forEach(str->{
            Matcher m=pat.matcher(str);

            if(m.find()) {
                Vertex v=Vertex.getVertex(m.group(1), Integer.parseInt(m.group(2)), m.group(3).split(", "));
                if(v.flow>0) { nonZeroFlow.add(v); System.out.print(v.id+"("+v.flow+") ");}
            }
        });
        nonZeroFlow.sort((v1, v2) -> {
            if (v1.flow == v2.flow) return 0;
            return v1.flow > v2.flow ? -1 : 1;
        });
        System.out.println();

        for(Vertex v:nonZeroFlow) System.out.print(v.id+"("+v.flow+") ");
        System.out.println();
        fw=new FloydW(Vertex.list.toArray(new Vertex[0]));
        Vertex cur=Vertex.all.get("AA");
        State2 init=new State2(cur.idx,nonZeroFlow);
        System.out.println(init);
        System.out.println("===>"+init.eval());

    }

    class State {

        State(int pos,int turn,List<Integer> open) {this.pos=pos;this.turn=turn;this.toBeOpen =open;}
        State(State o) {this.pos=o.pos;this.turn=o.turn;this.toBeOpen =new ArrayList<>(); this.toBeOpen.addAll(o.toBeOpen);this.flow=o.flow;}
        int pos,turn,flow=0;// position in tree,turn number
        List<Integer> toBeOpen;
        @Override public boolean equals(Object o) {
            if(o instanceof State) {
                State s=(State)o;
                if(s.turn==turn && s.pos==pos && s.toBeOpen.size()== toBeOpen.size()) {
                    for(int i = 0; i< toBeOpen.size(); ++i) if(!toBeOpen.get(i).equals(s.toBeOpen.get(i))) return false;
                }
                return true;
            }
            return false;
        }
        @Override
        public int hashCode() {
            return Objects.hash(pos, turn, toBeOpen);
        }

        int eval() {
            if(toBeOpen.size() ==0|| turn>=30)
                return flow;
            int res=0,i;
            for(int n:toBeOpen) {
                Vertex next=Vertex.list.get(n);
                i=next(next).eval();
                if(i>res) res=i;
            }
            //System.out.println(this+" "+res);
            return res;
        }
        @Override public String toString() {
            StringBuilder sb=new StringBuilder();
            Vertex cur=Vertex.list.get(pos);
            sb.append(" ".repeat(toBeOpen.size()));
            sb.append(cur.id).append('[');
            for(int i: toBeOpen) sb.append(Vertex.list.get(i).id).append(",");
            sb.deleteCharAt(sb.length()-1).append("] ").append(flow);
            return sb.toString();
        }
        State next(Vertex v) {
            State res=new State(this);
            int dist=fw.min[pos][v.idx];
            res.turn+=1+dist;
            res.pos=v.idx;
            if(turn<30) {
                res.toBeOpen.remove(Integer.valueOf(v.idx));
                res.flow += (30 - res.turn) * v.flow;
            }
            return res;
        }
    }

    class State2 {
        State2(int pos,List<Vertex> open) {this.pos1=pos;this.pos2=pos;this.toBeOpen =open;}
        State2(State2 o) {
            this.pos1=o.pos1;this.pos2=o.pos2;
            this.turn1=o.turn1;this.turn2=o.turn2;
            this.toBeOpen =new ArrayList<>(o.toBeOpen);
            this.flow=o.flow;
        }
        int pos1,pos2,turn1=0,turn2=0,flow=0;// position in tree,turn number

        List<Vertex> toBeOpen;

        int eval() {

            if(toBeOpen.size() ==0|| turn1>=24 && turn2>=24) {
                if(flow>best) {
                    best=flow;
                    System.out.println(flow+"normal->"+this);
                }
                return flow;
            }
            Integer prev;
            if((prev=known.get(this)) != null) {
                //if(prev.intValue()<flow) System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                return prev;
            }
            if(cantBeatBest()) {
                return flow;
            }
            int res=flow,i;
            List<Vertex> clone1,clone2;
            clone1=toBeOpen;clone2=toBeOpen;
            /*
            clone1 = new ArrayList<>(toBeOpen);
            clone2 = new ArrayList<>(toBeOpen);
            if(clone1.size()>1) {
                clone1.sort((v1, v2) -> {
                    if (v1 == v2) return 0;
                    return (26 - turn1 - fw.min[pos1][v1.idx] - 1) * v1.flow > (26 - turn1 - fw.min[pos1][v2.idx] - 1) * v2.flow ? -1 : 1;
                });
                clone2.sort((v1, v2) -> {
                    if (v1 == v2) return 0;
                    return (26 - turn2 - fw.min[pos2][v1.idx] - 1) * v1.flow > (26 - turn2 - fw.min[pos2][v2.idx] - 1) * v2.flow ? -1 : 1;
                });
            }
            */

            State2 theNext;
            for (Vertex next1 : clone1) {
                for (Vertex next2 : clone2) {
                    if (next1 != next2) {
                        if((theNext=next(next1, next2))== null) continue;
                        i = theNext.eval();

                        if (i > res) {
                            res = i;
                            if(toBeOpen.size()>=14) {
                                System.out.println(res);
                            }
                        }
                    }
                }
            }
            for(Vertex next2:clone2) {
                if((theNext=next(null, next2))== null) continue;
                i=theNext.eval();
                if (i > res) {
                    res=i;
                    if(toBeOpen.size()>=14) {
                        System.out.println(res);
                    }
                }
            }

            for(Vertex next1:clone1) {
                if((theNext=next(next1, null))== null) continue;
                i=theNext.eval();
                if (i > res) {
                    res = i;
                    if(toBeOpen.size()>=14) {
                        System.out.println(res);
                    }
                }
            }

            //System.out.println(this+" "+res);
            if(toBeOpen.size() >5)known.put(this,res);
            return res;
        }

        private boolean cantBeatBest() {
            int newf=0,idxOpen=0;
            for(int t1=turn1+2,t2=turn2+2;(t1<26||t2<26)&&idxOpen<toBeOpen.size();t1+=2,t2+=2) {
                if(t1>=26) {
                    newf+=(26-t2)*toBeOpen.get(idxOpen++).flow;
                }
                else if(t2>=26) {
                    newf+=(26-t1)*toBeOpen.get(idxOpen++).flow;

                } else {
                    if(t1>t2) {
                        newf+=(26-t2)*toBeOpen.get(idxOpen++).flow;
                        if(idxOpen==toBeOpen.size()) break;
                        newf+=(26-t1)*toBeOpen.get(idxOpen++).flow;
                    } else {
                        newf+=(26-t1)*toBeOpen.get(idxOpen++).flow;
                        if(idxOpen==toBeOpen.size()) break;
                        newf+=(26-t2)*toBeOpen.get(idxOpen++).flow;
                    }
                }
            }
            return (best > flow + newf);
        }

        @Override public String toString() {
            StringBuilder sb=new StringBuilder();
            Vertex cur1=Vertex.list.get(pos1),cur2=Vertex.list.get(pos2);
            sb.append(" ".repeat(toBeOpen.size()));
            sb.append(cur1.id).append(',').append(cur2.id).append('[');
            for(Vertex v: toBeOpen) sb.append(v.id).append(",");
            sb.deleteCharAt(sb.length()-1).append("] ").append(flow);
            return sb.toString();
        }
        State2 next(Vertex v1,Vertex v2) {
            State2 res=new State2(this);
            boolean changed=false;
            if(v1 != null) {
                int dist1=fw.min[pos1][v1.idx];

                if(turn1+1+dist1<26) {
                    res.turn1+=1+dist1;
                    res.pos1=v1.idx;
                    res.toBeOpen.remove(v1);
                    res.flow += (26 - res.turn1) * v1.flow;
                    changed=true;
                }
            }
            if(v2 != null) {
                int dist2=fw.min[pos2][v2.idx];

                if(turn2+1+dist2<26) {
                    res.turn2+=1+dist2;
                    res.pos2=v2.idx;
                    res.toBeOpen.remove(v2);
                    res.flow += (26 - res.turn2) * v2.flow;
                    changed=true;
                }
            }
            return changed?res:null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State2 s = (State2) o;
            return (pos1 == s.pos1 && pos2 == s.pos2 && turn1 == s.turn1 && turn2 == s.turn2) ||
                    (pos1 == s.pos2 && pos2 == s.pos1 && turn1 == s.turn2 && turn2 == s.turn1)
                && toBeOpen.equals(s.toBeOpen);
        }

        @Override
        public int hashCode() {
            int p1,p2;
            if(pos1<pos2) {
                p1= pos1 + turn1 <<16;
                p2=pos2 + turn2<<16;
            } else {
                p2= pos1 + turn1 <<16;
                p1=pos2 + turn2<<16;
            }
            return Objects.hash(p1,p2, toBeOpen);
        }



    }


    static class Vertex extends FloydW.Node{
        static HashMap<String,Vertex> all=new HashMap<>();
        static ArrayList<Vertex> list=new ArrayList<>();
        String id;
        int idx;
        int flow;
        String[] next;
        int[] nexti;
        Vertex(String id,int flow,String[] next,int idx) {this.id=id;this.next=next;this.flow=flow;this.idx=idx;}
        static Vertex getVertex(String id,int flow,String[] next) {
            Vertex res= new Vertex(id,flow,next,all.size());
            all.put(id,res);
            list.add(res);
            return res;
        }

        @Override
        public int[] next() {
            if(nexti==null) {
                nexti = new int[next.length];
                for (int i = 0; i < nexti.length; ++i) nexti[i] = all.get(next[i]).idx;
            }
            return nexti;
        }

        @Override
        public int dist(int idx) {
            return 1;
        }
        @Override
        public String toString() { return id;}
    }

}
