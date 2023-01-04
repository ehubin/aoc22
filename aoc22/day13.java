package aoc22;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class day13 extends b{
    int res;
    ArrayList<Elem> list=new ArrayList<>();


    public static void main(String[] a) {
        day13 d=new day13();
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
            if(str.length()>0) list.add(new Elem(str));
        });

        for (int i=0;i<list.size()/2;++i) if(list.get(2*i).compareTo(list.get(2*i+1))<=0) res+=(i+1);
        for (int i=0;i<list.size()/2;++i) {
            int comp= list.get(2*i).compareTo(list.get(2*i+1));
            System.out.println(/*list.get(2*i)+*/(comp<0 ? "<":comp==0?"=":">")+ list.get(2*i+1));
        }
        System.out.println(res);
    }



    @Override
    void processP2(Stream<String> s) {
        s.forEach(str->{
            if(str.length()>0) list.add(new Elem(str));
        });
        Elem d1=new Elem("[[2]]"),d2=new Elem("[[6]]");
        list.add(d1);
        list.add(d2);
        Collections.sort(list);
        //for(Elem e:list) System.out.println(e);
        System.out.println((1+list.indexOf(d1))*(1+list.indexOf(d2)));

    }
    static class Elem implements Comparable<Elem> {
        List<Elem> list=null;
        int intval=-1;
        Elem(String s) {this(s,new int[] {0});}
        Elem(int val) { this("["+Integer.toString(val)+"]");}
        Elem(String s,int[] i) {
            if(s.charAt(i[0])=='[') {
                list = new ArrayList<Elem>();
                ++i[0];
                if(s.charAt(i[0])==']') {
                    ++i[0];
                    return;
                }
                while(true) {
                    list.add(new Elem(s,i));
                    if(s.charAt(i[0])==',') {
                        ++i[0];
                    } else break;
                }
                ++i[0];
                return;
            } else {
                readInt(s,i);
            }
        }
        void readInt(String s,int[] i) {
            int end=i[0];
            do ++end; while(s.charAt(end) <='9' && s.charAt(end) >='0');
            intval = Integer.parseInt(s.substring(i[0],end));
            i[0]=end;
        }

        public String toString() {
            if (intval>=0) return Integer.toString(intval);
            else {
                StringBuilder sb= new StringBuilder();
                sb.append('[');
                for(Elem e:list) {sb.append(e);sb.append(',');}
                if(sb.length()>1) sb.deleteCharAt(sb.length() - 1);
                sb.append(']');
                return sb.toString();
            }

        }
        @Override
        public int compareTo(Elem o) {
            if(list==null && o.list ==null) return Integer.compare(intval,o.intval);
            if(list!= null && o.list != null) {
                for(int i=0; i<Math.min(list.size(),o.list.size());++i) {
                    int c=list.get(i).compareTo(o.list.get(i));
                    if(c!= 0) return c;
                }
                return list.size() > o.list.size() ? 1 :list.size() == o.list.size() ? 0:-1;
            }
            if(list!= null) {
                return this.compareTo(new Elem(o.intval));
            } else {
                return new Elem(intval).compareTo(o);
            }

        }
    }


}
