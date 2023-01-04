# aoc22
My solutions in java for advent of code 2022

You might be interested in the b.java class which is the base class for all my daily solution classes. These solution classes have to respect the day\<x\> naming convention and the b class will automatically fetch the input from the server for you. In the main method you can select which part gets executed and where the input data is taken from (local input.txt file or from server)
Note that for the remote http fetching of input data, you first need to [retrieve the session cookie](https://www.cookieyes.com/blog/how-to-check-cookies-on-your-website-manually/) corresponding to your account and replace it in the b class session variable accordingly.

Your derived solution class has to implement one method for each problem part.There are 2 flavours you can use to achieve that:

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

