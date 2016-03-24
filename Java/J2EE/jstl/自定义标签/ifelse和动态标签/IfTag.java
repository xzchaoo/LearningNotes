package xzc.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IfTag extends SimpleTagSupport {
	public static final String IF_TAG_FLAG = "xzc_if_tag_flag";
	private boolean test;

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public void doTag() throws JspException, IOException {
		JspContext ctx = getJspContext();
		if (test) {
			getJspBody().invoke( null );
			ctx.setAttribute( IF_TAG_FLAG,true);
		} else {
			ctx.setAttribute( IF_TAG_FLAG, false );
		}
	}
}
