package xzc.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ShowTag extends SimpleTagSupport {
	private String content;

	public void doTag() throws JspException, IOException {
		JspWriter jw = this.getJspContext().getOut();
		jw.println( content );
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
