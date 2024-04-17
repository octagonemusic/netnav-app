package com.octagone.netnavbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "router_route_data")
public class RouterRouteData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int sourceRouterId;
    private int targetRouterId;
    private int weight;

    public RouterRouteData() {
    }

    public RouterRouteData(int id, int sourceRouterId, int targetRouterId, int weight) {
        this.id = id;
        this.sourceRouterId = sourceRouterId;
        this.targetRouterId = targetRouterId;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getSourceRouterId() {
        return sourceRouterId;
    }

    public int getTargetRouterId() {
        return targetRouterId;
    }

    public int getWeight() {
        return weight;
    }
}
