package xzc.p4.m1;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestM1 {
	@Test
	public void testSay(){
		M1 m1=new M1();
		assertEquals("your name is xzc",m1.say("xzc"));
	}
}