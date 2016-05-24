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

}
