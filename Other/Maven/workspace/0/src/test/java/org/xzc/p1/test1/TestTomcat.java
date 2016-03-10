package org.xzc.p1.test1;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestTomcat{
	@Test
	public void testFun(){
		Tomcat t=new Tomcat();
		t.fun();
		assertEquals(true,true);
	}
}