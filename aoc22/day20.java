package aoc22;

import java.util.*;
import java.util.stream.Stream;

public class day20 extends b{
    final long mult=811589153L;
    List<Long> list=new ArrayList<>();
    public static void main(String[] a) {
        day20 d=new day20();
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
            list.add(Long.parseLong(str));
        });
        List<Integer> move=new LinkedList<>();
        for(int i=0;i<list.size();++i) move.add(i);

        //System.out.println(Arrays.toString(move.toArray()));
        for(int i=0;i<list.size();++i) {
            //printList(move);
            move(move,i);
        }
        printList(move);
        for(int j:move) System.out.print(j+", "); System.out.println();
        int idx=move.indexOf(list.indexOf(0L));
        System.out.println(idx+","+list.size()+"|"+(idx+1000)%list.size()+","+(idx+2000)%list.size()+","+(idx+3000)%list.size());
        //System.out.println(move.get((idx+1000)%list.size())+","+list.get(move.get((idx+1000)%list.size())));
        //System.out.println(move.get((idx+2000)%list.size())+","+list.get(move.get((idx+2000)%list.size())));
        //System.out.println(move.get((idx+3000)%list.size())+","+list.get(move.get((idx+3000)%list.size())));
        System.out.println(list.get(move.get(((idx+3000)%list.size())))+list.get(move.get(((idx+2000)%list.size())))+list.get(move.get(((idx+1000)%list.size()))));
    }

    void move(List<Integer> m,int i) {
        long toMove=list.get(i);
        if(toMove==0) return;
        int idx=m.indexOf(i);
        int newIdx=(int)((idx+toMove)%(list.size()-1));
        while(newIdx<0) newIdx+=list.size()-1;
        m.remove(idx);
        m.add(newIdx,i);
    }

    @Override
    void processP2(Stream<String> s) {
        s.forEach(str -> {
            list.add(mult*Long.parseLong(str));
        });
        List<Integer> move=new LinkedList<>();
        for(int i=0;i<list.size();++i) move.add(i);

        //System.out.println(Arrays.toString(move.toArray()));
        for(int j=0;j<10;++j) {
            for (int i = 0; i < list.size(); ++i) {
                //printList(move);
                move(move, i);
            }
        }
        printList(move);
        for(int j:move) System.out.print(j+", "); System.out.println();
        int idx=move.indexOf(list.indexOf(0L));
        System.out.println(idx+","+list.size()+"|"+(idx+1000)%list.size()+","+(idx+2000)%list.size()+","+(idx+3000)%list.size());
        //System.out.println(move.get((idx+1000)%list.size())+","+list.get(move.get((idx+1000)%list.size())));
        //System.out.println(move.get((idx+2000)%list.size())+","+list.get(move.get((idx+2000)%list.size())));
        //System.out.println(move.get((idx+3000)%list.size())+","+list.get(move.get((idx+3000)%list.size())));
        System.out.println(list.get(move.get(((idx+3000)%list.size())))+list.get(move.get(((idx+2000)%list.size())))+list.get(move.get(((idx+1000)%list.size()))));
    }
    void printList(List<Integer> move) {
        System.out.print('[');
        for(int i:move) System.out.print(list.get(i)+", ");
        System.out.println(']');
    }
}
