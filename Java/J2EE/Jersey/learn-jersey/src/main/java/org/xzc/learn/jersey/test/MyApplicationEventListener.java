package org.xzc.learn.jersey.test;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
public class MyApplicationEventListener implements ApplicationEventListener {
	@Override
	public void onEvent(ApplicationEvent event) {
		switch (event.getType()) {
			case INITIALIZATION_START:
				break;
			case DESTROY_FINISHED:
				System.out.println("Application " + event.getResourceConfig().getApplicationName() + " destroyed. ");
				break;
		}
		System.out.println("onEvent");
	}

	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		System.out.println("onRequest");
		return null;
	}
}
