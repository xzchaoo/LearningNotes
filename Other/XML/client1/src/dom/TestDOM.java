package dom;

import static org.junit.Assert.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class TestDOM {
	@Test
	public void 用DOM修改XML() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document d = db.parse( TestDOM.class.getResourceAsStream( "1.xml" ) );
		// Document d=db.newDocument();
		Element e = (Element) d.getElementsByTagName( "itemList" ).item( 0 );

		// 进行修改
		e.setAttribute( "author222", "xzc" );

		DOMImplementationLS ls = (DOMImplementationLS) DOMImplementationRegistry.newInstance().getDOMImplementation( "LS" );
		LSSerializer s = ls.createLSSerializer();
		s.getDomConfig().setParameter( "format-pretty-print", true );
		LSOutput out = ls.createLSOutput();
		out.setEncoding( "utf-8" );
		out.setByteStream( System.out );
		s.write( d, out );
	}

	@Test
	public void 用DOM创建XML() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document d = db.newDocument();
		Element root = d.createElement( "root" );
		root.setAttribute( "author", "xzc" );
		d.appendChild( root );
		Element p = d.createElement( "person" );
		p.setAttribute( "name", "xzc" );
		p.setAttribute( "age", "20" );
		root.appendChild( p );

		DOMImplementationLS ls = (DOMImplementationLS) DOMImplementationRegistry.newInstance().getDOMImplementation( "LS" );
		LSSerializer s = ls.createLSSerializer();
		System.out.println( s.getDomConfig().getClass() );
		s.getDomConfig().setParameter( "charset-overrides-xml-encoding", "utf-8" );
		LSOutput out = ls.createLSOutput();
		out.setEncoding( "utf-8" );
		out.setByteStream( System.out );
		// s.write( d, out );
		s.getDomConfig().setParameter( "encoding", "utf-8" );
		System.out.println( "xml=" + s.writeToString( d ) );
	}

	@Test
	public void 用DOM读取XML() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document d = db.parse( TestDOM.class.getResourceAsStream( "1.xml" ) );
		// Document d=db.newDocument();
		Element e = (Element) d.getElementsByTagName( "itemList" ).item( 0 );
		NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); ++i) {
			Node n = nl.item( i );
			if (!( n instanceof Element ))
				continue;
			System.out.println( n.getNodeName() );
		}
	}
}
