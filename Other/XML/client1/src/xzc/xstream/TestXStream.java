package xzc.xstream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TestXStream {
	// 修改XML
	@Test
	public void testName() throws Exception {
		// 使用xparse需要把整个文档读入进来
		InputStream is = TestXStream.class.getResourceAsStream( "2.xml" );
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse( is );
		XPath xpath = XPathFactory.newInstance().newXPath();

		// 获取要修改的节点
		NodeList nl = (NodeList) xpath.evaluate( "//person[@name='xzc1']", doc, XPathConstants.NODESET );
		Element e = (Element) nl.item( 0 );
		e.setAttribute( "name", "xzc3" );

		// 通过t将修改后的结果写出去
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
		Result r = new StreamResult( System.out );
		t.setOutputProperty( OutputKeys.INDENT, "yes" );
		t.transform( new DOMSource( doc ), r );
	}

	// 写XML
	@Test
	public void test4() throws XMLStreamException, FactoryConfigurationError {
		XMLStreamWriter xsw = XMLOutputFactory.newInstance().createXMLStreamWriter( System.out );
		xsw.writeStartDocument( "utf-8", "1.0" );
		xsw.writeStartElement( "ns", "books", "http://www.baidu.com" );
		xsw.writeStartElement( "book" );
		xsw.writeAttribute( "age", "20" );
		xsw.writeEndElement();

		xsw.writeEndElement();
		xsw.writeEndDocument();

		xsw.close();

	}

	// 读XML
	@Test
	public void test3() throws Exception {
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStream is = TestXStream.class.getResourceAsStream( "2.xml" );
		XMLEventReader xer = xif.createXMLEventReader( is );
		// 用filter过滤一些无用的节点 提高效率
		xer = xif.createFilteredReader( xer, new EventFilter() {// 套一个过滤器
					public boolean accept(XMLEvent event) {
						return event.isStartElement();// && "persons".equals(event.asStartElement().getName().toString());
					}
				} );

		List<Person> list = null;

		while (xer.hasNext()) {
			XMLEvent event = xer.nextEvent();
			StartElement se = event.asStartElement();
			String name = se.getName().toString();
			switch (name) {
			case "persons":
				list = new ArrayList<Person>();
				break;
			case "person":
				Person p = new Person();
				Iterator i = se.getAttributes();
				while (i.hasNext()) {
					Attribute a = (Attribute) i.next();
					String aname = a.getName().toString();
					String avalue = a.getValue();
					BeanUtils.setProperty( p, aname, avalue );
				}
				list.add( p );
				p = null;
				break;
			}
		}
		System.out.println( list );
	}

	@Test
	public void test2() throws Exception {
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStream is = TestXStream.class.getResourceAsStream( "2.xml" );
		XMLEventReader xer = xif.createXMLEventReader( is );

		List<Person> list = null;
		Person p = null;

		while (xer.hasNext()) {
			XMLEvent event = xer.nextEvent();
			if (event.isStartElement()) {
				StartElement se = event.asStartElement();
				String name = se.getName().toString();
				switch (name) {
				case "persons":
					list = new ArrayList<Person>();
					break;
				case "person":
					p = new Person();
					Iterator i = se.getAttributes();
					while (i.hasNext()) {
						Attribute a = (Attribute) i.next();
						String aname = a.getName().toString();
						String avalue = a.getValue();
						BeanUtils.setProperty( p, aname, avalue );
					}
					break;
				}
			} else if (event.isEndElement()) {
				String name = event.asEndElement().getName().toString();
				if ("person".equals( name )) {
					list.add( p );
				}
			}
		}
		System.out.println( list );
	}

	@Test
	public void test1() throws Exception {
		// 基于光标的方式慢慢读入xml
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStream is = TestXStream.class.getResourceAsStream( "2.xml" );
		XMLStreamReader xsr = xif.createXMLStreamReader( is );

		List<Person> list = null;
		Person p = null;

		while (xsr.hasNext()) {
			int event = xsr.next();
			String name = xsr.hasName() ? xsr.getName().toString() : null;
			switch (event) {
			case XMLStreamReader.START_ELEMENT:
				if ("persons".equals( name )) {
					list = new ArrayList<Person>();
				} else if ("person".equals( name )) {
					p = new Person();
					int count = xsr.getAttributeCount();
					for (int i = 0; i < count; ++i) {
						String aname = xsr.getAttributeName( i ).toString();
						String avalue = xsr.getAttributeValue( i );
						BeanUtils.setProperty( p, aname, avalue );
					}
				}
				break;
			case XMLStreamReader.END_ELEMENT:
				if ("person".equals( xsr.getName().toString() )) {
					list.add( p );
					p = null;
				}
				break;
			}
		}
		System.out.println( list );
	}
}
