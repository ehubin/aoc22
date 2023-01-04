package aoc22;

import aoc21.day15;

import java.util.Arrays;
import java.util.List;

public class FloydW {
    int[][] min;
    int[][] next;
    FloydW(Node[] nodes) {
        int size=nodes.length;
        min=new int[size][size];
        next=new int[size][size];
        for(int[] i:min) Arrays.fill(i,Integer.MAX_VALUE/2);
        for(int[] i:next) Arrays.fill(i,-1);
        for(int i=0;i<size;++i) {
            int[] nidx=nodes[i].next();
            for(int j=0;j<nidx.length;++j) {
                min[i][nidx[j]] = nodes[i].dist(j);
                next[i][nidx[j]] = nidx[j];
            }
        }
        for(int i=0;i<size;++i) {
            min[i][i]=0;
            next[i][i]=i;
        }

        for(int k=0;k<size;++k) {
            for(int i=0;i<size;++i) {
                for(int j=0;j<size;++j) {
                    if(min[i][j] > min[i][k]+min[k][j]) {
                        min[i][j] = min[i][k]+min[k][j];
                        next[i][j]=next[i][k];
                    }
                }
            }
        }
    }

    static abstract class Node {
                public abstract int[] next();
                public abstract int dist(int idx);
    }
}

