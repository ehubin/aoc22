package aoc22;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class day19 extends b{

    public static void main(String[] a) {
        day19 d=new day19();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}
    }

    @Override
    void processP1(Stream<String> s) {
        Pattern pat= Pattern.compile(".*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*");
        List<Blueprint> list=new ArrayList<>();
        s.forEach(str -> {
            Matcher m=pat.matcher(str.substring(20));
            if(m.find())  list.add(new Blueprint(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)),
                    Integer.parseInt(m.group(4)),
                    Integer.parseInt(m.group(5)),
                    Integer.parseInt(m.group(6))));
        });
        int res=0;
        for(int id=0;id<list.size();++id) {
            //System.out.println(b);
            State init=new State(list.get(id));
            State.known.clear();
            State.best=0;
            int max=init.max(24);
            res+=max*(id+1);
            System.out.println("Blueprint "+(id+1)+ " creates "+max+" geodes");

        }
        System.out.println(res);

    }



    @Override
    void processP2(Stream<String> s) {
        Pattern pat= Pattern.compile(".*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*");
        List<Blueprint> list=new ArrayList<>();
        s.forEach(str -> {
            Matcher m=pat.matcher(str.substring(20));
            if(m.find())  list.add(new Blueprint(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)),
                    Integer.parseInt(m.group(4)),
                    Integer.parseInt(m.group(5)),
                    Integer.parseInt(m.group(6))));
        });
        int res=1;
        for(int id=0;id<list.size() && id<3;++id) {
            //System.out.println(b);
            State init=new State(list.get(id));
            State.known.clear();
            State.best=0;
            int max=init.max(32);
            res*=max;
            System.out.println("Blueprint "+(id+1)+ " creates "+max+" geodes");

        }
        System.out.println(res);
    }
    static class Blueprint {
        int[][] required=new int[4][3];
        Blueprint(int oreO,int oreC,int oreOb,int clayOb,int oreG,int obG) {
            required[0][0]=oreO;
            required[1][0]=oreC;
            required[2][0]=oreOb;required[2][1]=clayOb;
            required[3][0]=oreG;required[3][2]=obG;
        }
        public String toString() {
            StringBuilder sb=new StringBuilder();
            for(int[] i:required) sb.append(Arrays.toString(i)).append("\n");
            return sb.toString();
        }
    }
    static class State {
        static HashMap<State,Integer> known=new HashMap<>();
        static int best=0;
        int[] nb=new int[4];
        int[] nbRobot = new int[] {1,0,0,0};
        Blueprint bp;
        int turn=0;
        State(Blueprint bp) {this.bp=bp;}
        State(State o) {
            nb=Arrays.copyOf(o.nb,4);
            nbRobot = Arrays.copyOf(o.nbRobot,4);
            bp=o.bp;
            turn=o.turn;
        }
        int max(int turnMax) {
            if(turn==turnMax) {
                if(nb[3]>best) {
                    best=nb[3];
                    //System.out.println(best+"==>"+this);
                }
                return nb[3];
            }
            Integer kmax=known.get(this);
            if(kmax!= null) {
                //System.out.println("k"+turn);
                return kmax;
            }
            if(bestPossible(turnMax)<=best) return nb[3];
            int res=0,cur;
            for(int i=3;i>=0;--i) {
                State next=nextState(i,turnMax);
                if(next==null) continue;
                cur=next.max(turnMax);
                if(cur>res) {
                    res=cur;
                }
            }
            if(turn < 20)known.put(this,res);
            return res;
        }
        int bestPossible(int turnMax) {
            int t=(turnMax-turn);
            return nb[3]+t*nbRobot[3]+t*(t-1)/2;
        }
        int timeToRob(int type) {
            int[] req=bp.required[type];
            int res=0,r;
            for(int i=0;i<3;++i) {
                r=req[i];
                if(r<=nb[i]+nbRobot[i]*res) continue;
                if(nbRobot[i]>0) {
                    r-=nb[i];
                    res=r/nbRobot[i];
                    if(r%nbRobot[i]>0) ++res;
                }
                else return -1;
            }
            return res;
        }
        State nextState(int robotType,int turnMax) {
            State res=new State(this);
            if(res.next(robotType,turnMax)) return res;
            return null;
        }
        boolean next(int robotType,int turnMax) {
            int t= 1+timeToRob(robotType);
            if(t==0) return false;
            if(turn+t>=turnMax) {
                t=turnMax-turn;
                turn=turnMax;
                for(int i=0;i<4;++i) nb[i] += t*nbRobot[i];
            } else {
                int[] req=bp.required[robotType];
                turn+=t;
                for (int i = 0; i < 3; ++i) nb[i] += t * nbRobot[i] - req[i];
                nb[3]+=t*nbRobot[3];
                ++nbRobot[robotType];
            }
            return true;
        }
        @Override public String toString() {
            String sb = turn + "->" +
                    Arrays.toString(nb) + "\n" +
                    Arrays.toString(nbRobot);
            return sb;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return turn == state.turn && Arrays.equals(nb, state.nb) && Arrays.equals(nbRobot, state.nbRobot);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(turn);
            result = 31 * result + Arrays.hashCode(nb);
            result = 31 * result + Arrays.hashCode(nbRobot);
            return result;
        }
    }
}
