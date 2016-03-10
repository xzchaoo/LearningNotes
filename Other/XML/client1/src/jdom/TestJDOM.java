package jdom;

import static org.junit.Assert.*;

import java.util.List;

import org.dom4j.DocumentFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.junit.Test;

public class TestJDOM {
	// 跟dom4j差不多 jdom是更多基于实现类的
	@Test
	public void 用JDOM创建XML() throws Exception {
		Document d = new Document();
		Element root = new Element( "root" );
		d.setRootElement( root );
		Element p = new Element( "person" );
		p.setAttribute( "name", "xzc" );
		root.addContent( p );
		Format f = Format.getPrettyFormat();
		f.setIndent( "\t" ).setEncoding( "utf-8" );
		XMLOutputter out = new XMLOutputter( f );
		out.output( d, System.out );
	}

	@Test
	public void 用JDOM读取XML() throws Exception {
		SAXBuilder sb = new SAXBuilder( false );
		Document d = sb.build( TestJDOM.class.getResourceAsStream( "1.xml" ) );
		Element root = d.getRootElement();
		List list = root.getChildren();
		for (Object obj : list) {
			Element e = (Element) obj;
			System.out.println( e.getName() );
		}
	}

	@Test
	public void 使用XPATH() throws Exception {
		SAXBuilder sb = new SAXBuilder();
		Document d = sb.build( TestJDOM.class.getResourceAsStream( "1.xml" ) );
		Element root = d.getRootElement();
		XPath xpath = XPath.newInstance( "item1/name" );
		List list = xpath.selectNodes( root );
		System.out.println( list );
	}
}
