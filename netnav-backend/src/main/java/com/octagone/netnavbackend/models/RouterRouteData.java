package com.octagone.netnavbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    private double bandwidth; // in Mbps
    private double latency; // in ms

    public RouterRouteData() {
    }

    public RouterRouteData(int id, int sourceRouterId, int targetRouterId, double bandwidth, double latency) {
        this.id = id;
        this.sourceRouterId = sourceRouterId;
        this.targetRouterId = targetRouterId;
        this.bandwidth = bandwidth;
        this.latency = latency;
        this.weight = calculateWeight();
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

    public double getBandwidth() {
        return bandwidth;
    }

    public double getLatency() {
        return latency;
    }

    public int getWeight() {
        return weight;
    }

    @PrePersist
    @PreUpdate
    private void updateWeight() {
        this.weight = calculateWeight();
    }

    @Transient
    private int calculateWeight() {
        // Time to transmit 1MB = (size in bits) / (bandwidth in bits per second) +
        // latency
        // Convert size to bits: 1MB = 8 * 1024 * 1024 bits
        // Convert bandwidth to bits per second: 1Mbps = 1 * 10^6 bits per second
        // Convert latency to seconds: 1ms = 0.001 seconds
        double time = (8 * 1024 * 1024) / (bandwidth * Math.pow(10, 6)) + latency * 0.001;
        // Convert time to milliseconds and round to the nearest integer
        return (int) Math.round(time * 1000);
    }
}
