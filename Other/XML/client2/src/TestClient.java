import org.junit.Test;
import org.xzc.service.impl.FirstWs;
import org.xzc.service.impl.FirstWsNoName;

public class TestClient {
	@Test
	public void testName() throws Exception {
		FirstWsNoName s = new FirstWsNoName();
		FirstWs fw = s.getFirstWsImplPort();
		System.out.println( fw.sayHi( "牛人" ) );
		System.out.println( fw.getUserList() );
		System.out.println(fw.getUserMap().getEntry().get( 0 ).getValue());
	}
}
