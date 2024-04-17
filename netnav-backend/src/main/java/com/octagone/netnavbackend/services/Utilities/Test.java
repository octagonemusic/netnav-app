package com.octagone.netnavbackend.services.Utilities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.octagone.netnavbackend.models.ShortestPathData;

import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

public class Test {
    @SuppressWarnings("null")
    public static void main(String args[]) {
        int start = 1;
        int end = 9;
        RestTemplate restTemplate = new RestTemplate();
        // make a request to the shortestPathMethod
        ResponseEntity<ShortestPathData> response = restTemplate.exchange(
                "http://localhost:8080/routerRouteData/shortestPath?start=1&end=9",
                HttpMethod.GET, null, new ParameterizedTypeReference<ShortestPathData>() {
                });
        ShortestPathData shortestPathData = response.getBody();

        System.out.println(
                "The shortest distance from " + start + " to " + end + " is: "
                        + shortestPathData.getShortestDistance());
        System.out.println("The shortest route is: " + shortestPathData.getShortestRoute());
    }
}
