package org.xzc.learn.jersey.client;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.client.ServiceLocatorClientProvider;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
public class MyFilterAndInterceptor implements ClientRequestFilter, ClientResponseFilter {
	public void filter(ClientRequestContext req) throws IOException {
		ServiceLocator sl = ServiceLocatorClientProvider.getServiceLocator(req);
		req.getHeaders().putSingle("a","b");
		System.out.println("client请求了");
	}

	public void filter(ClientRequestContext req, ClientResponseContext resp) throws IOException {
		System.out.println("client响应了");
	}
}
