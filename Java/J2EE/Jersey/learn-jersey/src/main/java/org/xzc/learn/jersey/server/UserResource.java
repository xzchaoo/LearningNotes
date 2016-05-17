package org.xzc.learn.jersey.server;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ChunkedOutput;
import org.glassfish.jersey.server.JSONP;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
@Singleton
@Path("/user")
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
public class UserResource {
	private Map<Integer, User> mUserMap = new HashMap<Integer, User>();
	private AtomicInteger mNextId = new AtomicInteger(0);

	@PostConstruct
	public void onConstruct() {
		mUserMap.put(0, new User(0, "xzc0", 0));
	}


	@GET
	@Path("/{id:\\d}")
	public Response get(@Min(2) @PathParam("id") int id) {
		User user = mUserMap.get(id);
		return user == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(user).build();
	}

	@GET
	@Path("/xml/{id:\\d}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getXML(@PathParam("id") int id) {
		User user = mUserMap.get(id);
		return user == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(user).build();
	}

	@MyAnnotation
	@GET
	@Path("/jsonp/{id:\\d}")
	@JSONP(queryParam = "callback")
	@Produces("application/javascript")
	public Response getJSONP(@PathParam("id") int id) {
		User user = mUserMap.get(id);
		return user == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(user).build();
	}

	@POST
	public Response post(
		@Context UriInfo ui,
		@QueryParam("a") List<String> a,
		@HeaderParam("x-from") String from,
		@CookieParam("a") String ca,
		User user) {
		System.out.println("ca=" + ca);
		System.out.println("from=" + from);
		System.out.println("a=" + a);
		user.id = mNextId.incrementAndGet();
		mUserMap.put(user.id, user);
		return Response.created(UriBuilder.fromMethod(UserResource.class, "get").build(user.id)).entity(user).build();
		//return Response.created(UriBuilder.fromMethod(UserResource.class, "get").build(user.id)).build();
	}

	@PUT
	@Path("/{id:\\d}")
	public Response put(@PathParam("id") int id, User user) {
		user.id = id;
		mUserMap.put(id, user);
		return Response.accepted(user).build();
	}

	@DELETE
	@Path("/{id:\\d}")
	public Response delete(@PathParam("id") int id) {
		mUserMap.remove(id);
		return Response.ok().build();
	}

	@MyAnnotation
	@Path("/upload")
	@POST
	public String upload(@FormDataParam("author") String author, @FormDataParam("mypom") File mypom, FormDataMultiPart f, @Context HttpHeaders hh) {
		System.out.println(hh.getRequestHeaders());
		System.out.println("author=" + author);
		System.out.println("mypom=" + mypom.length() + " " + mypom.getAbsolutePath());
		//System.out.println("size="+f.getBodyParts().size());
		return "ok";
	}

	@Path("/sse1")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {
		final EventOutput eventOutput = new EventOutput();
		new Thread(new Runnable() {
			public void run() {
				try {
					//发送10条信息回去 每次睡觉1秒
					for (int i = 0; i < 10; i++) {
						final OutboundEvent.Builder eventBuilder
							= new OutboundEvent.Builder();
						eventBuilder.name("message-to-client");
						eventBuilder.data(String.class,
							"Hello world " + i + "!");
						final OutboundEvent event = eventBuilder.build();
						eventOutput.write(event);
						Thread.sleep(1000);
					}
				} catch (IOException e) {
					throw new RuntimeException(
						"Error when writing the event.", e);
				} catch (InterruptedException e) {
					throw new RuntimeException(
						"Error when writing the event.", e);
				} finally {
					try {
						eventOutput.close();
					} catch (IOException ioClose) {
						throw new RuntimeException(
							"Error when closing the event output.", ioClose);
					}
				}
			}
		}).start();
		return eventOutput;
	}

	private SseBroadcaster broadcaster = new SseBroadcaster();

	@Path("/sse2/post")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String broadcastMessage(String message) {
		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("message")
			.mediaType(MediaType.TEXT_PLAIN_TYPE)
			.data(String.class, message)
			.build();

		broadcaster.broadcast(event);

		return "Message '" + message + "' has been broadcast.";
	}

	@Path("/sse2/get")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput listenToBroadcast() {
		final EventOutput eventOutput = new EventOutput();
		this.broadcaster.add(eventOutput);
		return eventOutput;
	}

	@Inject
	Provider<MonitoringStatistics> monitoringStatisticsProvider;

	@Path("/tongji")
	@GET
	public Date tongji() {
		try {
			MonitoringStatistics m = monitoringStatisticsProvider.get();
			return m.getRequestStatistics().getLastStartTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

