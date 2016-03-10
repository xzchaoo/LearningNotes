package xsl;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

public class TestTransformer {
	@Test
	public void test() throws Exception {
		//利用xls将xml转成html
		Transformer t = TransformerFactory.newInstance().newTransformer( new StreamSource( TestTransformer.class.getResourceAsStream( "4.xsl" ) ) );
		t.transform( new StreamSource( TestTransformer.class.getResourceAsStream( "1.xml" ) ), new StreamResult( System.out ) );
	}
}
