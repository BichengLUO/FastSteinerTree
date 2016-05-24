package com.faststeinertree;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by eaglesky on 5/24/16.
 */
public class FastSteinerTree {
    private int[][] inputGraph;
    private int[] inputSteinerPoints;

    private int[][] steinerPointsGraph;
    private int[][] steinerTree;
    private HashMap<Edge, HashSet<Edge>> edgeMap;

    public static int[][] minimalSpanningTree(int[][] graph) {
        return graph;
    }

    public static void addEdge(int[][] graph, Edge e, int dist) {
        graph[e.getNode1()][e.getNode2()] = graph[e.getNode2()][e.getNode1()] = dist;
    }

    public FastSteinerTree(int[][] iGraph, int[] iSteinerPoints) {
        inputGraph = iGraph.clone();
        inputSteinerPoints = iSteinerPoints.clone();
    }

    public int[][] execute() {
        constructSteinerPointsGraph();
        steinerPointsGraph = minimalSpanningTree(steinerPointsGraph);
        constructSubgraph();
        steinerTree = minimalSpanningTree(steinerTree);
        removeNonsteinerNodes();
        return steinerTree;
    }

    public void constructSteinerPointsGraph() {
        steinerPointsGraph = new int[inputSteinerPoints.length][inputSteinerPoints.length];

    }

    public void constructSubgraph() {

    }

    public void removeNonsteinerNodes() {

    }

    public static void main(String[] args) {
        int[][] graph = new int[9][9];
        addEdge(graph, new Edge(0, 1), 20);
        addEdge(graph, new Edge(0, 8), 2);
        addEdge(graph, new Edge(7, 8), 1);
        addEdge(graph, new Edge(6, 7), 1);
        addEdge(graph, new Edge(5, 6), 2);
        addEdge(graph, new Edge(4, 5), 2);
        addEdge(graph, new Edge(4, 9), 2);
        addEdge(graph, new Edge(1, 5), 2);
        addEdge(graph, new Edge(1, 2), 16);
        addEdge(graph, new Edge(2, 4), 4);
        addEdge(graph, new Edge(2, 3), 18);
        addEdge(graph, new Edge(3, 4), 4);

        FastSteinerTree fst = new FastSteinerTree(graph, new int[]{0 ,1, 2, 3});
        int[][] st = fst.execute();
    }

}
