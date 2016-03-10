package dom4j;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class TestDOM4J {
	// 修改保存=读取+修改+写出
	@Test
	public void 用DOM4J创建XML() throws Exception {
		Document d = DocumentHelper.createDocument();
		Element root = d.addElement( "root" );
		d.setRootElement( root );
		Element p = root.addElement( "person" );
		p.addAttribute( "name", "xzc" );
		Writer w = new OutputStreamWriter( System.out );
		d.write( w );
		w.flush();
	}

	@Test
	public void 用DOM4J读取XML() throws Exception {
		SAXReader r = new SAXReader( false );
		Document d = r.read( TestDOM4J.class.getResourceAsStream( "1.xml" ) );
		Element root = d.getRootElement();
		System.out.println( root.getName() );
	}
}
