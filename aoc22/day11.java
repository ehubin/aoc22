package aoc22;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

public class day11 extends b{
    int res;
    ArrayList<Monkey> monkeys = new ArrayList<>();

    public static void main(String[] a) {
        day11 d=new day11();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }


    @Override
    void processP1(Stream<String> s) {
        Iterator<String> it=s.iterator();
        while(it.hasNext()) monkeys.add(new Monkey(it));
        //for(Monkey m:monkeys) System.out.println(m);
        for(int i=0;i<20;++i) {
            System.out.println("round "+(i+1));
            for(Monkey m:monkeys) {
                m.inspect(monkeys);
                System.out.println (m.res);
            }

        }
        monkeys.sort(Comparator.comparing(Monkey::getRes).reversed());
        System.out.println(monkeys.get(0).res*monkeys.get(1).res);
    }


    @Override
    void processP2(Stream<String> s) {
        Iterator<String> it=s.iterator();
        int divt=1;
        while(it.hasNext()) monkeys.add(new Monkey(it));
        for(Monkey m:monkeys) divt*= m.div;
        System.out.println(divt);
        //for(Monkey m:monkeys) System.out.println(m);
        for(int i=0;i<10000;++i) {
            System.out.println("round "+(i+1));
            for(Monkey m:monkeys) {
                m.inspect2(monkeys,divt);
                System.out.println (m.res);
            }

        }
        monkeys.sort(Comparator.comparing(Monkey::getRes).reversed());
        System.out.println(monkeys.get(0).res*monkeys.get(1).res);

    }
    public static class Monkey {
        ArrayList<Long> items=new ArrayList<>();
        boolean mult,op2;
        int op,div,toTrue,toFalse;
        long res;

        Monkey(Iterator<String> s) {
            while(true){ if(s.next().startsWith("M")) break;}
            for(String str:s.next().substring(18).split(", ")) items.add(Long.parseLong(str));
            String opStr=s.next().substring(23);
            mult=opStr.charAt(0)=='*';
            op2=opStr.charAt(2)=='o';
            if(!op2) op = Integer.parseInt(opStr.substring(2));
            div=Integer.parseInt(s.next().substring(21));
            toTrue=Integer.parseInt(s.next().substring(29));
            toFalse=Integer.parseInt(s.next().substring(30));
        }
        public String toString() {
            StringBuilder sb=new StringBuilder();
            for(long item:items) sb.append(item).append(',');
            sb.append("\n");
            sb.append(mult? "* ":"+ ").append(op2? "old":op);
            sb.append("\ndiv ").append(div);
            sb.append("\ntrue ").append(toTrue).append(" - false ").append(toFalse);
            return sb.toString();
        }
        long getRes() { return res;}

        void inspect(ArrayList<Monkey> ml) {
            for(long item:items) {
                long tmp1= mult? item * (op2 ? item : op) : item + (op2 ? item : op);
                long tmp2=tmp1/3;
                if(tmp2 % div == 0) ml.get(toTrue).items.add(tmp2);
                else ml.get(toFalse).items.add(tmp2);
            }
            res+=items.size();
            items.clear();
        }
        void inspect2(ArrayList<Monkey> ml,int divt) {
            for(long item:items) {
                long tmp2= mult? item * (op2 ? item : op) : item + (op2 ? item : op);
                if(tmp2 % div == 0) ml.get(toTrue).items.add(tmp2%divt);
                else ml.get(toFalse).items.add(tmp2%divt);
            }
            res+=items.size();
            items.clear();
        }
    }
}
