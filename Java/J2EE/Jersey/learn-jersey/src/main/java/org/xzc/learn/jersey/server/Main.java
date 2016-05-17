package org.xzc.learn.jersey.server;

import org.eclipse.persistence.jaxb.BeanValidationMode;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.xzc.learn.jersey.client.MyExceptionMapper;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
public class Main {
	public static final URI BASE_URI = URI.create("http://localhost:8080/rs");

	public static class MyResponseFilter implements ContainerResponseFilter {

		public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
			String contentType = responseContext.getHeaderString("Content-Type");
			if (contentType != null && !contentType.contains("charset")) {
				contentType += ";charset=utf-8";
				try {
					//responseContext.getStringHeaders()("Content-Type", Arrays.asList(contentType));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void startServer() {
		try {
			ResourceConfig rc = new ResourceConfig();
			rc.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
			rc.register(MyResponseFilter.class);
			rc.register(MultiPartFeature.class);
			rc.property("jersey.config.server.monitoring.statistics.enabled",true);

			MoxyJsonConfig mjc = new MoxyJsonConfig();
			mjc.setAttributePrefix("abc");

			mjc.property(MarshallerProperties.BEAN_VALIDATION_MODE, BeanValidationMode.NONE);

			rc.register(mjc.resolver());

///			rc.register(MyExceptionMapper.class);

			rc.packages("org.xzc.learn.jersey.server");
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc, false);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					server.shutdown();
				}
			}));
			server.start();
			System.out.println("启动了");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		startServer();
	}
}
