package com.octagone.netnavbackend.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.octagone.netnavbackend.models.ShortestPathData;
import com.octagone.netnavbackend.services.Utilities.Pair;

import java.util.ArrayList;

@Component
public class BestRouteFinderService {
    @Autowired
    ConvertRouteDataToMap convertRouteDataToMap;

    @Autowired
    ShortestPathFinder shortestPathFinder;

    // find the best route given a 2d array using djikstra's shortest path algorithm
    public ShortestPathData findBestRoute(int start, int end) {
        // get Hashmap from the Instance Variable
        HashMap<Integer, ArrayList<Pair>> map = convertRouteDataToMap.getRouterRouteMap();
        // find the best route
        ShortestPathData bestRoute = shortestPathFinder.shortestPath(map, start, end);
        return bestRoute;
    }

}
