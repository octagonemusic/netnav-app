package com.octagone.netnavbackend.models;

import java.util.List;

public class ShortestPathData {
    private int shortestDistance;
    private List<Integer> shortestRoute;

    public ShortestPathData(int shortestDistance, List<Integer> shortestRoute) {
        this.shortestDistance = shortestDistance;
        this.shortestRoute = shortestRoute;
    }

    public int getShortestDistance() {
        return shortestDistance;
    }

    public List<Integer> getShortestRoute() {
        return shortestRoute;
    }
}
