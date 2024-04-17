package com.octagone.netnavbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.octagone.netnavbackend.services.BestRouteFinderService;
import com.octagone.netnavbackend.events.NewRouterRouteDataEvent;
import com.octagone.netnavbackend.models.RouterRouteData;
import com.octagone.netnavbackend.models.ShortestPathData;
import com.octagone.netnavbackend.repositories.RouterRouteDataRepository;

@RestController
@RequestMapping("/routerRouteData")
public class RouterRouteDataController {
    @Autowired
    RouterRouteDataRepository routerRouteDataRepository;

    @Autowired
    BestRouteFinderService bestRouteFinderService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<RouterRouteData>> getRouterRouteData() {
        return new ResponseEntity<>(routerRouteDataRepository.findAll(), HttpStatus.OK);
    }

    @SuppressWarnings("null")
    @PostMapping("/addRoute")
    public ResponseEntity<?> addRouterRouteData(@RequestBody RouterRouteData routerRouteData) {
        // prevent duplicate routes
        List<RouterRouteData> routes = getRouterRouteData().getBody();
        for (RouterRouteData route : routes) {
            // check if any of the request params are empty
            if (routerRouteData.getSourceRouterId() == 0 || routerRouteData.getTargetRouterId() == 0
                    || routerRouteData.getWeight() == 0) {
                return new ResponseEntity<>("Route not added. One or more of the request parameters are empty",
                        HttpStatus.BAD_REQUEST);
            }

            if (route.getSourceRouterId() == routerRouteData.getSourceRouterId()
                    && route.getTargetRouterId() == routerRouteData.getTargetRouterId()) {
                // throw an exception
                return new ResponseEntity<>("Route already exists", HttpStatus.BAD_REQUEST);
            }
        }
        RouterRouteData savedRouterRouteData = routerRouteDataRepository.save(routerRouteData);
        eventPublisher.publishEvent(new NewRouterRouteDataEvent(this, savedRouterRouteData));
        return new ResponseEntity<>("Route added", HttpStatus.OK);
    }

    @GetMapping("/shortestPath")
    public ResponseEntity<?> getShortestPath(@RequestParam int start, int end) {
        if (start == 0 || end == 0) {
            return new ResponseEntity<>("One or more of the request parameters are empty", HttpStatus.BAD_REQUEST);
        }

        ShortestPathData bestRoute = bestRouteFinderService.findBestRoute(start, end);
        return new ResponseEntity<>(bestRoute, HttpStatus.OK);
    }
}