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
        HashSet<Integer> wholeSet = new HashSet<>();
        HashSet<Integer> vSet = new HashSet<>();

        int start = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                if (graph[i][j] != 0) {
                    wholeSet.add(i);
                    wholeSet.add(j);
                    start = i;
                }
            }
        }

        vSet.add(start);
        while (vSet.size() < wholeSet.size()) {
            int minWeight = Integer.MAX_VALUE;
            Edge mwe = new Edge(-1, -1);
            for (int i = 0; i < graph.length; i++) {
                for (int j = i + 1; j < graph.length; j++) {
                    if (mst[i][j] == 0 && graph[i][j] != 0) {
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
            if (minWeight != Integer.MAX_VALUE) {
                vSet.add(mwe.getNode1());
                vSet.add(mwe.getNode2());
                mst[mwe.getNode1()][mwe.getNode2()] = mst[mwe.getNode2()][mwe.getNode1()] = minWeight;
                //System.out.printf("[Prim] Add edge (%d, %d)\n", mwe.getNode1(), mwe.getNode2());
            }
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
        return new ShortestPathWithDist(shortestPath, d[target]);
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
        constructSubGraph();
        //printGraphEdges(steinerTree);

        steinerTree = prim(steinerTree);
        removeNonSteinerLeaves();
        //printGraphEdges(steinerTree);

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

    public void constructSubGraph() {
        steinerTree = new int[inputGraph.length][inputGraph.length];
        for (int i = 0; i < steinerPointsGraph.length; i++) {
            for (int j = i + 1; j < steinerPointsGraph.length; j++) {
                if (steinerPointsGraph[i][j] != 0) {
                    List<Edge> shortestPath = edgeMap.get(new Edge(i, j));
                    if (shortestPath == null) {
                        System.out.println("[Error] Can't find in edgeMap");
                    }
                    for (Edge e : shortestPath) {
                        int ei = e.getNode1();
                        int ej = e.getNode2();
                        steinerTree[ei][ej] = steinerTree[ej][ei] = inputGraph[ei][ej];
                    }
                }
            }
        }
    }

    public void removeNonSteinerLeaves() {
        int[] removeNodes = new int[steinerTree.length];
        for (int i = 0; i < inputSteinerPoints.length; i++) {
            removeNodes[inputSteinerPoints[i]] = 1;
        }
        int cleanCount;
        do {
            cleanCount = 0;
            for (int i = 0; i < steinerTree.length; i++) {
                if (removeNodes[i] == 0) {
                    int degree = 0;
                    int j;
                    for (j = 0; j < steinerTree.length; j++) {
                        if (steinerTree[i][j] != 0) {
                            degree++;
                        }
                    }
                    if (degree == 1) {
                        removeNodes[i] = -1;
                        steinerTree[i][j] = steinerTree[j][i] = 0;
                        cleanCount++;
                    }
                }
            }
        } while (cleanCount > 0);
    }

    public static void printGraphEdges(int[][] graph) {
        int edgeCount = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                if (graph[i][j] > 0) {
                    System.out.printf("(%d, %d) ", i, j);
                    edgeCount++;
                }
            }
        }
        if (edgeCount == 0) {
            System.out.println("Empty graph!");
        } else {
            System.out.println();
        }
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

        System.out.println("Input Graph:");
        printGraphEdges(graph);

        FastSteinerTree fst = new FastSteinerTree(graph, new int[]{0 ,1, 2, 3});
        int[][] st = fst.execute();

        System.out.println("Steiner Tree:");
        printGraphEdges(st);
    }

}
