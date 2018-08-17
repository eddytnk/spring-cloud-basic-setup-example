package com.in28minutes.microservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/currency-converter")
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private CurrencyExchangeServicePoxy proxy;
	
	
	@GetMapping("from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurency(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		String uri = "http://localhost:{port}/currency-exchange/from/{from}/to/{to}";
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
	    uriVariables.put("port", "8000");
		
		ResponseEntity<CurrencyConversionBean> responseEntity = 
				new RestTemplate().getForEntity(uri, CurrencyConversionBean.class,uriVariables);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		logger.info("{}", response);
		
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,
				quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
	
	
	@GetMapping("/feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurencyFeign(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,
				quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
}
