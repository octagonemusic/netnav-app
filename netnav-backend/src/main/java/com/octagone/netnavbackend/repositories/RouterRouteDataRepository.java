package com.octagone.netnavbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.octagone.netnavbackend.models.RouterRouteData;

@RepositoryRestResource
public interface RouterRouteDataRepository extends JpaRepository<RouterRouteData, Integer> {

}
