package xzc.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class TestJaxb {
	@Test
	public void test1() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance( User.class );
		Marshaller marshaller = ctx.createMarshaller();
		User stu = new User( 1, "xzc", 21 );
		marshaller.marshal( stu, System.out );
	}

	@Test
	public void test2() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><age>21</age><id>1</id><name>xzc</name></user>";
		JAXBContext ctx = JAXBContext.newInstance( User.class );
		Unmarshaller ummarshaller = ctx.createUnmarshaller();
		User stu = (User) ummarshaller.unmarshal( new StringReader( xml ) );
		System.out.println( stu );
	}
}
