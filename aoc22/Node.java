package aoc22;

import java.util.Comparator;
import java.util.List;

public abstract class Node<Id,N> implements Comparable<Node<Id,N>> {
    public Id id;
    public int cost;
    public int dist=Integer.MAX_VALUE;

    public Node(Id id, int cost)
    {
        this.id = id;
        this.cost = cost;
    }
    abstract List<N> adj();

    @Override public int compareTo(Node<Id,N> node)
    {
        if (dist< node.dist)
            return -1;

        if (dist > node.dist)
            return 1;

        return 0;
    }
    @Override public int hashCode() {
        return id.hashCode();
    }
    @Override public boolean equals(Object o) {
        return o instanceof Node ? id.equals(((Node)o).id):false;
    }
}
