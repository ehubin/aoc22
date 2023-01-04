package aoc22;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class day5 extends b{
    int res=0;
    boolean init=true;
    ArrayList<Stack<Character>> stacks= new ArrayList<>();
    ArrayList<ArrayList<Character>> tmp= new ArrayList<>();
    public static void main(String[] a) {
        day5 d=new day5();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }


    int getVal(char c) {return Character.isUpperCase(c)? c-'A'+27 : c-'a'+1;}
    @Override
    void processP1(Stream<String> s) {

        Pattern p= Pattern.compile("\\[(\\w)\\]\\s?|\\s\\s\\s\\s?");
        Pattern op= Pattern.compile("move (\\d+) from (\\d) to (\\d)");
        for(int i=0;i<10;++i) tmp.add(new ArrayList<>());
        for(int i=0;i<10;++i) stacks.add(new Stack<>());
        s.forEach(str -> {
            //System.out.println(str);
            if(init) {
                if(str.charAt(1)=='1') {
                    System.out.println("endInit");
                    for(int i=1;i<10;++i) {
                        ArrayList<Character> cur=tmp.get(i);
                        Stack<Character> curs = stacks.get(i);
                        for(int j=cur.size()-1;j>=0;--j) curs.push(cur.get(j));
                    }
                    init=false;
                }
                Matcher m = p.matcher(str);
                int i = 1;
                while (m.find()) {
                    if(m.group(1) != null) tmp.get(i).add(m.group(1).charAt(0));
                    ++i;
                }
            } else {
                Matcher m = op.matcher(str);
                if(m.find()) {
                    int nb=Integer.parseInt(m.group(1));
                    int from=Integer.parseInt(m.group(2));
                    int to=Integer.parseInt(m.group(3));
                    Stack<Character> fr =stacks.get(from);
                    Stack<Character> t =stacks.get(to);
                    //System.out.println(nb+","+from+","+to);
                    for(int i=0;i<nb;++i) t.push(fr.pop());
                }
            }
        });
        for(int i=1;i<10;++i) System.out.print(stacks.get(i).pop());
    }


    @Override
    void processP2(Stream<String> s) {
        Pattern p= Pattern.compile("\\[(\\w)\\]\\s?|\\s\\s\\s\\s?");
        Pattern op= Pattern.compile("move (\\d+) from (\\d) to (\\d)");
        for(int i=0;i<10;++i) tmp.add(new ArrayList<>());
        for(int i=0;i<10;++i) stacks.add(new Stack<>());
        s.forEach(str -> {
            //System.out.println(str);
            if(init) {
                if(str.charAt(1)=='1') {
                    System.out.println("endInit");
                    for(int i=1;i<10;++i) {
                        ArrayList<Character> cur=tmp.get(i);
                        Stack<Character> curs = stacks.get(i);
                        for(int j=cur.size()-1;j>=0;--j) curs.push(cur.get(j));
                    }
                    init=false;
                }
                Matcher m = p.matcher(str);
                int i = 1;
                while (m.find()) {
                    if (m.group(1) != null) tmp.get(i).add(m.group(1).charAt(0));
                    ++i;
                }
            } else {
                Matcher m = op.matcher(str);
                if(m.find()) {
                    int nb=Integer.parseInt(m.group(1));
                    int from=Integer.parseInt(m.group(2));
                    int to=Integer.parseInt(m.group(3));
                    Stack<Character> fr =stacks.get(from);
                    Stack<Character> t =stacks.get(to);
                    //System.out.println(nb+","+from+","+to);
                    char[] temp= new char[nb];
                    for(int i=0;i<nb;++i) temp[i]=fr.pop();
                    for(int i=nb-1;i>=0;--i) t.push(temp[i]);
                }
            }
        });
        for(int i=1;i<10;++i) System.out.print(stacks.get(i).pop());
    }
}
