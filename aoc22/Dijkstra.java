package aoc22;

import java.util.*;
import java.util.stream.Stream;

public class Dijkstra<N extends Node<Id,N>,Id> {
    private Set<N> settled=new HashSet<>();
    private List<N> pq= new LinkedList<>();

    public void dijkstra( N src)
    {
        pq.clear();
        settled.clear();
        // Add source node to the priority queue
        pq.add(src);
        // Distance to the source is 0
        src.dist = 0;

        while (true) {

            // Terminating condition check when
            // the priority queue is empty, return
            if (pq.isEmpty())
                return;
            // Removing the minimum distance node
            // from the priority queue
            N next=pq.remove(0);
            // Adding the node whose distance is
            // finalized
            if (settled.contains(next))  continue;
            settled.add(next);
            e_Neighbours(next);
        }
    }
    N get(Id i) {
        Iterator<N> it=settled.iterator();
        N cur;
        while(it.hasNext()) if((cur=it.next()).id.equals(i)) return cur;
        return null;
    }
    Stream<N> getAll() { return settled.stream();}

    // To process all the neighbours
    // of the passed node
    private void e_Neighbours(N u)
    {
        //System.out.println("Processing "+u);
        int edgeDistance = -1;
        int newDistance = -1;
        List<N> adj=u.adj();
        // All the neighbors of v
        for (int i = 0; i < adj.size(); i++) {
            N v= adj.get(i);

            // If current node hasn't already been processed
            if (!settled.contains(v)) {
                edgeDistance = v.cost;
                newDistance = u.dist + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < v.dist) {
                    pq.remove(v);
                    v.dist = newDistance;
                    // Add the current node to the queue
                    pq.add(v);
                }
            }
        }
    }
}
