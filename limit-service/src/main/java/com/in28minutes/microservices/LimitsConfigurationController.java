package com.in28minutes.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration config;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromCongiguration() {
		return new LimitConfiguration(config.getMaximum(),config.getMinimum());
	}
	
	@GetMapping("/hystrx-example/limits")
	@HystrixCommand(fallbackMethod="fallbackretrieveCongiguration")
	public LimitConfiguration retrieveCongiguration() {
		throw new RuntimeException("Not available");
	}
	
	public LimitConfiguration fallbackretrieveCongiguration() {
		return new LimitConfiguration(9999,9);
	}
}
