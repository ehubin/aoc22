package aoc22;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class b {
    int day;
    final String httpSession = "53616c7465645f5fb0ba9a4d55ea01cfd531851fd3d138b6b174ec622f8d7c2299b377fef7efe2f41234abcdb471ef1064ce1d38ea5d62b55b355c4a41ee7e4d";
    b() {
        day = Integer.parseInt(this.getClass().getName().split("day")[1]);
    }
    Stream<String> getTxtStream() throws Exception{
        return new BufferedReader(
                new InputStreamReader(new FileInputStream("test.txt"), StandardCharsets.UTF_8)).lines();
    }
    Stream<String> getHttpStream(int i) throws Exception{
        HttpURLConnection c = (HttpURLConnection) new URL("https://adventofcode.com/2022/day/"+i+"/input").openConnection();
        c.setRequestProperty("Cookie", "session="+httpSession);
        return new BufferedReader(
                new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8)).lines();
    }
    @SuppressWarnings("unused")
    void pt1() throws Exception { processP1(getTxtStream());}
    @SuppressWarnings("unused")
    void ph1() throws Exception { processP1(getHttpStream(day));}
    @SuppressWarnings("unused")
    void pt2() throws Exception { processP2(getTxtStream());}
    @SuppressWarnings("unused")
    void ph2() throws Exception { processP2(getHttpStream(day));}
    @SuppressWarnings("unused")
    void p1_t() throws Exception { readInput(getTxtStream()); P1();}
    @SuppressWarnings("unused")
    void p1_h() throws Exception { readInput(getHttpStream(day)); P1();}
    @SuppressWarnings("unused")
    void p2_t() throws Exception { readInput(getTxtStream()); P2();}
    @SuppressWarnings("unused")
    void p2_h() throws Exception { readInput(getHttpStream(day)); P2();}
    void  processP1(Stream<String> s) {}
    void processP2(Stream<String> s) {}
    void P1() {}
    void P2() {}
    void readInput(Stream<String> in) {}
}
