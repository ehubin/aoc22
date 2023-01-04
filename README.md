# aoc22
My solutions in java for advent of code 2022

You might be interested in the b.java class which all solution classes for each day extend. Solution have to respect the day<x> naming convention and the b class will automatically fetch the input from the server for you.
your class has to implement one method for each problem part.

There are 2 flavours you can use:

If you want to factorize input reading between the part 1 and part 2:

```java
public class day1 extends b{    
    public static void main(String[] a) {
        day1 d=new day1();
        try {
            // part 1 with local text input
            //d.p1_t();
            // part 1 with remote http input
            //d.p1_h();
            //part 2 with local text input
            //d.p2_t();
            //part 2 with remote http input
            d.p2_h();
        } catch(Exception e) {e.printStackTrace();}

    }

    @Override
    void readInput(Stream<String> in) {
        in.forEach(str -> {
            // process each line here
        });
    }

    @Override
    void P1() {
        //solve part 1 here
    }

    @Override
    void P2() {
        //solve part 2 here
    }
}
```

If you want to directly process the input in each part function:

```java
public class day1 extends b{    
    public static void main(String[] a) {
        day1 d=new day1();
        try {
            //d.pt1();
            //d.ph1();
            //d.pt2();
            d.ph2();
        } catch(Exception e) {e.printStackTrace();}

    }

  
    @Override
    void processP1(Stream<String> in) {
        //solve part 1 here
    }

    @Override
    void processP2(Stream<String> in) {
        //solve part 2 here
    }
}
```

