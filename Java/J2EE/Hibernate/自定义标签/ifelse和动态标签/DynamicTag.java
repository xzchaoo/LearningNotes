package xzc.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DynamicTag extends SimpleTagSupport implements DynamicAttributes {
	public DynamicTag() {
		System.out.println( "DynamicTag" );
	}

	private Map<String, Object> map = new HashMap<String, Object>();

	public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
		System.out.println( name + " " + value );
		map.put( name, value );
	}

}
