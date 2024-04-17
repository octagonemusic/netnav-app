package com.octagone.netnavbackend.services;

import com.octagone.netnavbackend.events.NewRouterRouteDataEvent;
import com.octagone.netnavbackend.models.RouterRouteData;
import com.octagone.netnavbackend.repositories.RouterRouteDataRepository;
import com.octagone.netnavbackend.services.Utilities.Pair;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConvertRouteDataToMap {
    @Autowired
    private RouterRouteDataRepository routerRouteDataRepository;

    private HashMap<Integer, ArrayList<Pair>> RouterRouteMap = new HashMap<Integer, ArrayList<Pair>>();

    // private ConvertRouteDataToMap() {
    // RouterRouteData = new HashMap<Integer, ArrayList<Pair>>();
    // }

    @PostConstruct
    public void init() {
        try {
            // get all the routes from the database
            List<RouterRouteData> edges = routerRouteDataRepository.findAll();
            convert(edges);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventListener
    public void onNewRouterRouteDataEvent(NewRouterRouteDataEvent event) {
        List<RouterRouteData> routes = Collections.singletonList(event.getRouterRouteData());
        convert(routes);
    }

    public HashMap<Integer, ArrayList<Pair>> getRouterRouteMap() {
        return RouterRouteMap;
    }

    // convert List<RouterRouteData> to a hashmap
    public void convert(List<RouterRouteData> routes) {
        for (RouterRouteData route : routes) {
            int source = route.getSourceRouterId();
            int target = route.getTargetRouterId();
            int weight = route.getWeight();
            if (!RouterRouteMap.containsKey(source)) {
                RouterRouteMap.put(source, new ArrayList<Pair>());
            }
            if (!RouterRouteMap.containsKey(target)) {
                RouterRouteMap.put(target, new ArrayList<Pair>());
            }
            RouterRouteMap.get(source).add(new Pair(target, weight));
            RouterRouteMap.get(target).add(new Pair(source, weight));
        }
    }
}
