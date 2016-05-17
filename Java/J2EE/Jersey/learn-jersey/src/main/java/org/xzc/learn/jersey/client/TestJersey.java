package org.xzc.learn.jersey.client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xzc.learn.jersey.server.Main;
import org.xzc.learn.jersey.server.User;

import java.awt.PageAttributes;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import rx.functions.Action1;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
public class TestJersey {

	private Client c;
	private WebTarget t;

	@Before
	public void before() {
		Main.startServer();
		ClientConfig cc = new ClientConfig();
		cc.register(MyFilterAndInterceptor.class);
		cc.connectorProvider(new HttpUrlConnectorProvider());
		//cc.connectorProvider(new GrizzlyConnectorProvider());
		cc.register(MultiPartFeature.class);

		//可以设置属性的前缀 比如 xzc_
		//这样所有 XmlAttribute 都会加上前缀
		//但是Element不会加前缀

		MoxyJsonConfig mjc = new MoxyJsonConfig();
		mjc.setAttributePrefix("abc");
		cc.register(mjc.resolver());

		cc.register(MyExceptionMapper.class);

		ClientBuilder cb = ClientBuilder.newBuilder().withConfig(cc);
		c = cb.newClient(cc);
		t = c.target(Main.BASE_URI).path("/user");
	}

	@After
	public void after() {
		c.close();
	}

	@Test
	public void testRx() {

	}

	@Test
	public void testSimple() throws ExecutionException, InterruptedException {
		User user = new User();
		user.username = "张三";
		user.money = 777;
		user = t
			.queryParam("a", "1", "2", "3")
			.request()
			.header("x-from", "xzc-client")
			.cookie("a", "b")
			.post(Entity.json(user), User.class);
		System.out.println(user);

		RxObservable.from(t.path("/1")).queryParam("callback", "abc").request(MediaType.APPLICATION_JSON).rx().get(String.class).toBlocking().forEach(new Action1<String>() {
			public void call(String user) {
				System.out.println(user);
			}
		});
	}

	@Test
	public void testUpload() {
		//MultiPart mp = new MultiPart();
		//mp.bodyPart(new BodyPart().entity("OK"));\
		FormDataMultiPart f = new FormDataMultiPart();
		f.field("author", "xzchaoo");
		f.bodyPart(new FileDataBodyPart("mypom", new File("pom.xml")));
		String s = t.path("/upload").request().post(Entity.entity(f, f.getMediaType()), String.class);
		System.out.println(s);
	}
		/*
		WebTarget wt = t.path("/{id}").resolveTemplate("id", 1);
		String s = wt.request().get(String.class);
		System.out.println(s);
		System.exit(0);*/

	@Test
	public void testEventOutput() {
		EventInput ei = t.path("/sse1").request().get(EventInput.class);
		while (!ei.isClosed()) {
			InboundEvent ibe = ei.read();
			if (ibe == null) break;
			System.out.println(ei.read().readData());
		}
	}

	@Test
	public void testEventOutputAsync() throws InterruptedException {
		final EventSource eventSource = EventSource.target(t.path("/sse1")).build();
		eventSource.register(new EventListener() {
			public void onEvent(InboundEvent inboundEvent) {
				if (inboundEvent == null) {
					eventSource.close();
				} else {
					System.out.println(inboundEvent.readData());
				}
			}
		});
		eventSource.open();
		Thread.sleep(15000);
	}
}