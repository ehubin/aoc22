package aoc22;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day21 extends b {
    int res = 0;


    public static void main(String[] a) {
        day21 d = new day21();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    void processP1(Stream<String> s) {
        Pattern scanner= Pattern.compile("(\\w+).\\s(?:(\\d+)|(?:(\\w+) ([+\\-*/]) (\\w+)))");

        s.forEach(str -> {
            Matcher m=scanner.matcher(str);
            if(m.find()) {
                //System.out.println(m.group(0)+" "+m.group(1)+" "+m.group(2)+" "+m.group(3)+" "+m.group(4));
                if(m.group(2)==null) Op.createOp(m.group(1),m.group(3),m.group(4),m.group(5));
                else Op.createOp(m.group(1),Integer.parseInt(m.group(2)));
            }
        });
        System.out.println(Ops.get("root").exec());
    }


    @Override
    void processP2(Stream<String> s) {
        Pattern scanner= Pattern.compile("(\\w+).\\s(?:(\\d+)|(?:(\\w+) ([+\\-*/]) (\\w+)))");

        s.forEach(str -> {
            Matcher m=scanner.matcher(str);
            if(m.find()) {
                //System.out.println(m.group(0)+" "+m.group(1)+" "+m.group(2)+" "+m.group(3)+" "+m.group(4));
                if(m.group(2)==null) Op.createOp(m.group(1),m.group(3),m.group(4),m.group(5));
                else Op.createOp(m.group(1),Integer.parseInt(m.group(2)));
            }
        });
        Op human=Ops.get("humn"),root=Ops.get("root");
        human.op= Op.Operation.none;
        Op p1=Ops.get(root.p1);
        Op p2=Ops.get(root.p2);
        int count=0;
        //p1.depH(human);
        //System.out.println(p1);
        //p2.depH(human);
        //System.out.println(p2);
        BigInteger v1,v2,vmid,h1=BigInteger.valueOf(0),h2=BigInteger.valueOf(Long.MAX_VALUE/100);
        human.num=h1;
        v1=p1.exec().subtract(p2.exec());
        human.num=h2;

        v2=p1.exec().subtract(p2.exec());
        System.out.println(v1+","+v2);
        /*final long MAX= 37203685477580L,step=100000000;

        for(long i=-MAX;i<MAX;i+=step) {
            human.num=BigInteger.valueOf(i);
            System.out.println(i+"=>"+(p1.exec().subtract(p2.exec())));
        }
        */
       while(true) {
            human.num=h1.add(h2).divide(BigInteger.TWO);
            vmid=p1.exec().subtract(p2.exec());
            System.out.println(vmid);
            if(vmid.intValue()==0) break;
            if(vmid.signum()*v1.signum() <=0) h2=human.num;
            else {h1=human.num;v1=p1.exec().subtract(p2.exec());}
        }
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());
        human.num=human.num.subtract(BigInteger.ONE);
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());
        human.num=human.num.subtract(BigInteger.ONE);
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());
        human.num=human.num.subtract(BigInteger.ONE);
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());
        human.num=human.num.subtract(BigInteger.ONE);
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());
        human.num=human.num.subtract(BigInteger.ONE);
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());
        human.num=human.num.subtract(BigInteger.ONE);
        System.out.println(human.num);
        System.out.println(p1.exec()+","+p2.exec());



    }
    static HashMap<String,Op> Ops=new HashMap<>();

static class Op {

        enum Operation{none,plus,minus,mult,div}
        BigInteger num;
        boolean depH;
        String p1;
        String p2;
        Operation op=Operation.none;
        Op(int i) {num=BigInteger.valueOf(i);}
        Op(String p1,String p2, char o) {
            this.p1=p1;this.p2=p2;
            switch(o) {
                case '+':op=Operation.plus;return;
                case '-':op=Operation.minus;return;
                case '*':op=Operation.mult;return;
                case '/':op=Operation.div;return;
            }
            throw new IllegalArgumentException("Argument "+o);
        }
        static void createOp(String name,int i) {
            Ops.put(name,new Op(i));
        }
        static void createOp(String name,String p1, String o,String p2) {
            Ops.put(name,new Op(p1,p2,o.charAt(0)));
        }
        boolean depH(Op human) {
            if(this == human) depH= true;
            else if (op==Operation.none) depH = false;
            else {
                boolean b1=Ops.get(p1).depH(human),b2=Ops.get(p2).depH(human);
                depH =  b1|| b2;
                if (!depH) {
                    num = exec();
                    op = Operation.none;
                }
            }
            return depH;
        }
        @Override public  String toString() {
            if(Ops.get("humn")==this) return "H";
            if (op==Operation.none) return num.toString();
            char opc=op==Operation.plus ? '+' : op==Operation.minus ? '-' : op==Operation.mult ? '*' : '/' ;
            String dpc= depH?"d":"n("+num+")";
            return "("+Ops.get(p1)+ opc+Ops.get(p2)+")";
        }

        BigInteger exec() {
            BigInteger res;
            switch(op) {
                case none: return num;
                case plus:
                    res= Ops.get(p1).exec().add( Ops.get(p2).exec());
                    //System.out.println(p1+"+"+p2+"="+res);
                    return res;
                case minus:
                    res= Ops.get(p1).exec().subtract( Ops.get(p2).exec());
                    //System.out.println(p1+"-"+p2+"="+res);
                    return res;
                case mult:
                    res= Ops.get(p1).exec().multiply( Ops.get(p2).exec());
                    //System.out.println(p1+"*"+p2+"="+res);
                    return res;
                case div:
                    res= Ops.get(p1).exec().divide(Ops.get(p2).exec());
                    //System.out.println(p1+"/"+p2+"="+res);
                    return res;
            }
            return null;
        }
}

}