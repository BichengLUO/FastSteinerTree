package com.faststeinertree;

/**
 * Created by eaglesky on 5/24/16.
 */
public class Edge {
    private int node1;
    private int node2;

    public Edge(int n1, int n2) {
        if (n1 < n2) {
            node1 = n1;
            node2 = n2;
        } else {
            node1 = n2;
            node2 = n1;
        }
    }

    public int getNode1() {
        return node1;
    }

    public void setNode1(int node1) {
        this.node1 = node1;
    }

    public int getNode2() {
        return node2;
    }

    public void setNode2(int node2) {
        this.node2 = node2;
    }
}
