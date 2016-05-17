package org.xzc.learn.jersey.server;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
@MyAnnotation
public class ContainerFilterAndInterceptor implements ContainerRequestFilter, ContainerResponseFilter, ReaderInterceptor, WriterInterceptor {
	public void filter(ContainerRequestContext req) throws IOException {
		System.out.println("req-filter");
	}

	public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
		System.out.println("req-resp-filter");
	}

	public Object aroundReadFrom(ReaderInterceptorContext ric) throws IOException, WebApplicationException {
		System.out.println("aroundReadFrom");
		return ric.proceed();
	}

	public void aroundWriteTo(WriterInterceptorContext wic) throws IOException, WebApplicationException {
		System.out.println("aroundWriteTo");
		wic.proceed();
	}
}
