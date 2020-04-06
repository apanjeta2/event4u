package com.event4u.eventsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {
    @Autowired
    private DiscoveryClient discoveryClient;

    public String getNotificationInstance() {
    List<String> services = new ArrayList<String>();
        discoveryClient.getInstances("notification-service").forEach(instance ->
        {
            services.add(String.format("%s", instance.getUri()));
        });
		return services.get(0);
    }
}
