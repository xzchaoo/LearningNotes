package xzc;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestHello {
	@Test
	public void testSayHi(){
		assertEquals(Hello.sayHi("xzc"),"Hi xzc");
	}
}