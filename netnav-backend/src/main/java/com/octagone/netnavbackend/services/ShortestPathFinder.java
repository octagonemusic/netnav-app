package com.octagone.netnavbackend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.octagone.netnavbackend.services.Utilities.PriorityQueue;
import com.octagone.netnavbackend.models.ShortestPathData;
import com.octagone.netnavbackend.services.Utilities.Pair;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class ShortestPathFinder {
    // find the shortest path using djikstra's algorithm
    public ShortestPathData shortestPath(HashMap<Integer, ArrayList<Pair>> map, int start, int end) {
        // create a priority queue
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
        // create a hashmap to store the distance of each vertex from the start vertex
        Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
        // create a predecessor map to store the path traversed from start to end vertex
        Map<Integer, Integer> pred = new HashMap<Integer, Integer>();

        for (Integer vertexId : map.keySet()) {
            dist.put(vertexId, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        // add the start node to the priority queue
        pq.insert(new Pair(start, 0));

        // while the priority queue is not empty
        while (!pq.isEmpty()) {
            Pair p = pq.extractMin();
            int source = p.getVertex();
            // int w = p.getWeight();

            // for each targetRouter of u. pls call the targetRouter as "targetConnections
            // or
            // targetRouters"
            for (Pair targetRouter : map.get(source)) {
                int vertex = targetRouter.getVertex();
                int weight = targetRouter.getWeight();

                // if the distance to v is greater than the distance to u + weight of edge u-v
                if (dist.get(vertex) > dist.get(source) + weight) {
                    // update the distance to v
                    dist.put(vertex, dist.get(source) + weight);
                    // update predecessor of v
                    pred.put(vertex, source);
                    // add v to the priority queue
                    pq.insert(new Pair(vertex, dist.get(vertex)));
                }
            }
        }

        // print the route
        List<Integer> route = new ArrayList<Integer>();
        for (Integer vertex = end; vertex != null; vertex = pred.get(vertex)) {
            route.add(vertex);
        }
        Collections.reverse(route);

        return new ShortestPathData(dist.get(end), route);
    }
}
