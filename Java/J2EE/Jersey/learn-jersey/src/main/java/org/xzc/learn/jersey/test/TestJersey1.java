package org.xzc.learn.jersey.test;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
public class TestJersey1 extends JerseyTest {

	@Path("hello")
	public static class HelloResource {
		@GET
		public String getHello() {
			return "Hello World!";
		}
	}

	@Override
	protected Application configure() {
		//配置服务端
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		//set(TestProperties.RECORD_LOG_LEVEL, Level.ALL);
		return new ResourceConfig(HelloResource.class).register(MyApplicationEventListener.class);
	}

	//这是一个在内存里的 test container factory 实现, 对于简单的测试很快
	private final TestContainerFactory testContainerFactory = new InMemoryTestContainerFactory();

	@Override
	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
		return testContainerFactory;
	}

	@Override
	protected void configureClient(ClientConfig config) {
		//配置客户端

	}

	@Test
	public void test() {
		String hello = target("hello").request().get(String.class);
		assertEquals("Hello World!", hello);
		//hello = target("hello").request().get(String.class);
		//assertEquals("Hello World!", hello);
	}
}
