package xzc.xstream;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestXPath {
	@Test
	public void test() throws Exception {
		// 使用xparse需要把整个文档读入进来
		InputStream is = TestXStream.class.getResourceAsStream( "2.xml" );
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse( is );
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nl = (NodeList) xpath.evaluate( "//person[@name='xzc1']", doc, XPathConstants.NODESET );
		for (int i = 0; i < nl.getLength(); ++i) {
			Node n = nl.item( i );
			Element e = (Element) n;
			System.out.println( e.getAttribute( "id " ) );
		}
	}
}
