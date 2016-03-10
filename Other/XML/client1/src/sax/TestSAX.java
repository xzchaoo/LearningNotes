package sax;

import static org.junit.Assert.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class TestSAX {
	@Test
	public void testName() throws Exception {
		//有需要的话 要启动命名空间识别 才能正确处理命名空间的相关问题
		XMLReader r = XMLReaderFactory.createXMLReader();
		r.setContentHandler( new DefaultHandler() {
			@Override//监听事件
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				System.out.println( "start" + qName );
			}
		} );
		r.parse( new InputSource( TestSAX.class.getResourceAsStream( "1.xml" ) ) );
	}

	@Test
	public void testName2() throws Exception {
		SAXParser p = SAXParserFactory.newInstance().newSAXParser();
		InputSource is = new InputSource( TestSAX.class.getResourceAsStream( "1.xml" ) );
		p.parse( is, new DefaultHandler() {

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				System.out.println( qName );
			}

		} );
	}
}
