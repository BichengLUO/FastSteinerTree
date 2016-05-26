package com.faststeinertree;

import java.util.Comparator;

/**
 * Created by nobodycrackme on 2016/5/26.
 */
public class dNodeComparator implements Comparator<dNode> {
    @Override
    public int compare(dNode a, dNode b) {
        return a.dist - b.dist;
    }
}
