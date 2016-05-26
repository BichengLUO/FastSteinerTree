package com.faststeinertree;

import java.util.List;

/**
 * Created by nobodycrackme on 2016/5/26.
 */
public class ShortestPathWithDist {
    public List<Edge> shortestPath;
    public int distance;

    public ShortestPathWithDist(List<Edge> s, int d) {
        shortestPath = s;
        distance = d;
    }
}
