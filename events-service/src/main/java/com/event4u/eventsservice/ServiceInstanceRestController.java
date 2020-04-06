package com.event4u.eventsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@RequestMapping(value = "/services", method = RequestMethod.GET)
	public List<String> getRegisteredServices()
	{
		List<String> services = new ArrayList<String>();
		discoveryClient.getServices().forEach(serviceName ->
		{
			discoveryClient.getInstances(serviceName).forEach(instance ->
			{
				services.add(String.format("%s:%s", serviceName, instance.getUri()));
			});
		});
		return services;
	}
}
