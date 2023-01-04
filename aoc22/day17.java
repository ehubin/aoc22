package aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class day17 extends b{
    int res=0;
    boolean right[];
    int turn=0,wpl;
    ArrayList<boolean[]> stopped=new ArrayList<>();


    public static void main(String[] a) {
        day17 d=new day17();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }
    void print(ArrayList<boolean[]> s) {
        for(int i=s.size()-1;i>=0;--i) {
            System.out.print("|");
            for(boolean b:stopped.get(i)) System.out.print(b?'#':'.');
            System.out.println("|");
        }
    }
    @Override
    void processP1(Stream<String> s) {
        String str = s.findFirst().get();
        wpl=str.length();
        System.out.println(wpl);
        right = new boolean[wpl];
        for (int i = 0; i < wpl; ++i) right[i] = str.charAt(i) == '>';

        int NB = 2022;
        for (int i = 0; i < NB; ++i) {
            Obj cur = new Obj(i,stopped.size());
            while(true) {
                boolean tor=right[turn%wpl];
                if(tor) cur.right(stopped); else cur.left(stopped);
                ++turn;
                if(!cur.down(stopped)) {
                    //print(stopped);
                    break;
                }
            }
        }
        //print(stopped);
        System.out.println(stopped.size());
    }


    @Override
    void processP2(Stream<String> s) {
        String str = s.findFirst().get();
        wpl=str.length();
        System.out.println("wpl="+wpl);
        right = new boolean[wpl];
        for (int i = 0; i < wpl; ++i) right[i] = str.charAt(i) == '>';

        int objIdx=0;
        ArrayList<int[]> ref=new ArrayList<>();
       while(true) {
            Obj cur = new Obj(objIdx++,stopped.size());
            while(true) {

                boolean tor=right[turn%wpl];
                if(tor) cur.right(stopped); else cur.left(stopped);
                ++turn;
                if(!cur.down(stopped)) {
                    //print(stopped);
                    break;
                }
            }
            int[] status = new int[] {objIdx%5,turn%wpl,cur.x,stopped.size(),objIdx};
            check:
            for(int i=0;i<ref.size();++i) {
                if(status[0] != 1) continue;
                int[] past=ref.get(i);
                for(int j=0;j<3;++j) if(status[j]!=past[j]) continue check;
                System.out.println("Found Period "+ Arrays.toString(status)+ " prev "+Arrays.toString(past)+" at "+(turn-1));
                long nbPeriod=(1000000000000L-objIdx)/(status[4]-past[4]);
                long remainder=(1000000000000L-objIdx)%(status[4]-past[4]);
                long res= status[3]+nbPeriod*(status[3]-past[3])+ref.get(i+(int)remainder)[3]-past[3];
                System.out.println(past[3]+","+ref.get(i+(int)remainder)[3]+","+nbPeriod*(status[3]-past[3]));
                System.out.println(res);
                //print(stopped);
                return;
            }
            ref.add(status);
        }
    }

    static class Obj {
        int w,h,x,y;
        boolean[][] pat;
        Obj(int turn,int res) {
            x=2;
            pat=PAT[turn%5];
            w=pat.length;
            h=pat[0].length;
            y=res+2+h;
        }
        void right(ArrayList<boolean[]> stopped) {
            //System.out.println("r");
            if((x+w)==7) return;
            if((y-h+1)<stopped.size()) {
                for(int i=0;i<w;++i) {
                    for(int j=0;j<h;++j) {
                        if(y-j<stopped.size() && pat[i][j] && stopped.get(y-j)[x+i+1]) return;
                    }
                }
            }
            ++x;
        }
        void left(ArrayList<boolean[]> stopped) {
            //System.out.println("l");
            if(x==0) return;
            if((y-h+1)<stopped.size()) {
                for(int i=0;i<w;++i) {
                    for(int j=0;j<h;++j) {
                        if(y-j<stopped.size() && pat[i][j] && stopped.get(y-j)[x+i-1]) return;
                    }
                }
            }
            --x;
        }
        boolean  down(ArrayList<boolean[]> stopped) {
            if((y-h)>=stopped.size()) {--y; return true;}
            for(int i=0;i<w;++i) {
                for(int j=0;j<h;++j) {
                    if(y-j-1<0||(y-j-1<stopped.size() && pat[i][j] && stopped.get(y-j-1)[x+i])) {
                        //System.out.println("Blocked @ "+x+","+y+"-"+i+","+j);
                        if(y+1>stopped.size()) {
                            int add=y-stopped.size()+1;
                            for(i=0;i<add;++i) stopped.add(new boolean[7]);
                        }
                        for(int k=0;k<w;++k) for(int l=0;l<h;++l) if(pat[k][l]) stopped.get(y-l)[x+k]=true;
                        return false;
                    }
                }
            }
            {--y; return true;}
        }

        static boolean[][][] PAT={
            {{true},{true},{true},{true}},
            {{false,true,false},
             {true,true,true},
             {false,true,false}},
            {{false,false,true},
             {false,false,true},
             {true,true,true}},
            {{true,true,true,true}},
            {{true,true},{true,true}}};

    }
}
