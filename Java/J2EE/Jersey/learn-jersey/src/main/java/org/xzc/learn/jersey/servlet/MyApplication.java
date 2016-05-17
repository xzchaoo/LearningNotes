package org.xzc.learn.jersey.servlet;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.uri.UriTemplate;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
@ApplicationPath("/rs")
public class MyApplication extends ResourceConfig {
	public MyApplication() {
		packages("org.xzc.learn.jersey.server");
	}
}
