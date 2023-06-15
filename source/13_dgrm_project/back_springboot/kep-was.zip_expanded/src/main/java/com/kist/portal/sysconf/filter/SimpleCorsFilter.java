package com.kist.portal.sysconf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCorsFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String originHeader = request.getHeader("Origin");
		if(originHeader != null && !"".equalsIgnoreCase(originHeader)) {
			originHeader = originHeader.replaceAll("\\r", " ").replaceAll("\\n", " ");
		}
		response.setHeader("Access-Control-Allow-Origin", originHeader);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Headers", "content-type, x-xsrf-token");
		response.setHeader("Access-Control-Allow-Headers", "x-pageid, x-dataserviceid, x-portalid, x-siteid, x-skiperrorhandler, x-requested-with, content-type, accept, x-xsrf-token, x-isdp-token, x-ISDP-System-Token, X-PINGOTHER");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Cache-Control" , "no-cache, no-store, max-age=0, must-revalidate");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}
