package aoc22;

import java.util.Arrays;
import java.util.Iterator;

public class infiniFor implements Iterator<int[]> {
    final int[][] def;
    int[] cur;
    infiniFor(int[][] def) {
        this.def=def;
        cur=new int[def.length];
        for(int i=0;i<def.length;++i) cur[i]=def[i][0];
    }
    @Override
    public boolean hasNext() {
        for(int i=0;i<def.length;++i) if(cur[i] <def[i][1]) return true;
        return false;
    }
    @Override
    public int[] next() {
        for(int i=def.length-1;i>=0d;--i) {
            if(cur[i] <def[i][1]) {
                ++cur[i];
                for(int j=def.length-1;j>i;--j) cur[j]=def[j][0];
                return cur;
            }
        }
       throw new Error("There was no next");
    }
    public static void main(String[] a) {
        infiniFor loop=new infiniFor(new int[][] { {0,3},{0,3},{1,4}});
        while(loop.hasNext()) System.out.println(Arrays.toString(loop.next()));
    }
}
