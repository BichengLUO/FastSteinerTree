package com.faststeinertree;

import java.util.*;

/**
 * Created by eaglesky on 5/24/16.
 */
public class FastSteinerTree {
    private int[][] inputGraph;
    private int[] inputSteinerPoints;

    private int[][] steinerPointsGraph;
    private int[][] steinerTree;
    private HashMap<Edge, List<Edge>> edgeMap;

    public static int[][] prim(int[][] graph) {
        int[][] mst = new int[graph.length][graph.length];
        HashSet<Integer> vSet = new HashSet<>();
        vSet.add(0);
        while (vSet.size() < graph.length) {
            int minWeight = Integer.MAX_VALUE;
            Edge mwe = new Edge(-1, -1);
            for (int i = 0; i < graph.length; i++) {
                for (int j = i + 1; j < graph.length; j++) {
                    if (mst[i][j] == 0) {
                        boolean iInJOut = vSet.contains(i) && !vSet.contains(j);
                        boolean iOutJIn = !vSet.contains(i) && vSet.contains(j);
                        boolean lessWeight = graph[i][j] < minWeight;
                        if ((iInJOut || iOutJIn) && lessWeight) {
                            minWeight = graph[i][j];
                            mwe.setNode1(i);
                            mwe.setNode2(j);
                        }
                    }
                }
            }
            vSet.add(mwe.getNode1());
            vSet.add(mwe.getNode2());
            mst[mwe.getNode1()][mwe.getNode2()] = mst[mwe.getNode2()][mwe.getNode1()] = minWeight;
        }
        return mst;
    }

    public static ShortestPathWithDist dijkstra(int[][] graph, int source, int target) {
        int[] d = new int[graph.length];
        int[] previous = new int[graph.length];
        HashSet<Integer> nodes = new HashSet<>();
        for (int i = 0; i < graph.length; i++) {
            d[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            nodes.add(i);
        }
        d[source] = 0;
        while (!nodes.isEmpty()) {
            int u = -1;
            int min_dist = Integer.MAX_VALUE;
            for (int node : nodes) {
                if (d[node] < min_dist) {
                    min_dist = d[node];
                    u = node;
                }
            }
            nodes.remove(u);
            if (u == target) {
                break;
            }
            for (int i = 0; i < graph.length; i++)
            {
                if (graph[u][i] > 0 && d[i] > d[u] + graph[u][i]) {
                    d[i] = d[u] + graph[u][i];
                    previous[i] = u;
                }
            }
        }
        ArrayList<Edge> shortestPath = new ArrayList<>();
        int u = target;
        while (previous[u] != -1) {
            shortestPath.add(new Edge(previous[u], u));
            u = previous[u];
        }
        Collections.reverse(shortestPath);
        ShortestPathWithDist result = new ShortestPathWithDist(shortestPath, d[target]);
        return result;
    }

    public static void addEdge(int[][] graph, Edge e, int dist) {
        graph[e.getNode1()][e.getNode2()] = graph[e.getNode2()][e.getNode1()] = dist;
    }

    public FastSteinerTree(int[][] iGraph, int[] iSteinerPoints) {
        inputGraph = iGraph;
        inputSteinerPoints = iSteinerPoints;
    }

    public int[][] execute() {
        constructSteinerPointsGraph();
        steinerPointsGraph = prim(steinerPointsGraph);
        constructSubgraph();
        steinerTree = prim(steinerTree);
        removeNonsteinerNodes();
        return steinerTree;
    }

    public void constructSteinerPointsGraph() {
        steinerPointsGraph = new int[inputSteinerPoints.length][inputSteinerPoints.length];
        edgeMap = new HashMap<>();
        for (int i = 0; i < inputSteinerPoints.length; i++) {
            for (int j = i + 1; j < inputSteinerPoints.length; j++) {
                ShortestPathWithDist spwd = dijkstra(inputGraph, inputSteinerPoints[i], inputSteinerPoints[j]);
                steinerPointsGraph[i][j] = steinerPointsGraph[j][i] = spwd.distance;
                edgeMap.put(new Edge(i, j), spwd.shortestPath);
            }
        }
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
        addEdge(graph, new Edge(4, 8), 2);
        addEdge(graph, new Edge(1, 5), 2);
        addEdge(graph, new Edge(1, 2), 16);
        addEdge(graph, new Edge(2, 4), 4);
        addEdge(graph, new Edge(2, 3), 18);
        addEdge(graph, new Edge(3, 4), 4);

        FastSteinerTree fst = new FastSteinerTree(graph, new int[]{0 ,1, 2, 3});
        int[][] st = fst.execute();
    }

}
