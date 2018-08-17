package com.in28minutes.microservices;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object run() throws ZuulException {
		//main logic of filter
		HttpServletRequest httpRequest = RequestContext.getCurrentContext().getRequest();
		
		logger.info("request -> {} request uri ->{}",httpRequest, httpRequest.getRequestURI());
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		//priority order of filler
		return 1;
	}

	@Override
	public String filterType() {
		//filter type for this request
		return "pre";  // "pre","post","error"
	}

}
