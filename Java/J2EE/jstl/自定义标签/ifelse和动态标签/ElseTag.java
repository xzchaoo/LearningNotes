package xzc.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ElseTag extends SimpleTagSupport {
	public void doTag() throws JspException, IOException {
		JspContext ctx = getJspContext();
		Boolean b = (Boolean) ctx.getAttribute( IfTag.IF_TAG_FLAG );
		if (b == null)
			throw new RuntimeException( "该elseif标签使用错误,请检查是否与一个if标签匹配!" );
		ctx.removeAttribute( IfTag.IF_TAG_FLAG );
		if (b)
			return;
		getJspBody().invoke( null );
	}
}
