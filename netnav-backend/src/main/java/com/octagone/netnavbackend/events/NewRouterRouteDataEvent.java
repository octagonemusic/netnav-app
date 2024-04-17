package com.octagone.netnavbackend.events;

import org.springframework.context.ApplicationEvent;

import com.octagone.netnavbackend.models.RouterRouteData;

public class NewRouterRouteDataEvent extends ApplicationEvent {
    private RouterRouteData routerRouteData;

    public NewRouterRouteDataEvent(Object source, RouterRouteData routerRouteData) {
        super(source);
        this.routerRouteData = routerRouteData;
    }

    public RouterRouteData getRouterRouteData() {
        return routerRouteData;
    }
}
