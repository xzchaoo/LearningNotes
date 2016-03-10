package spring_and_servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletToBeanHelper extends GenericServlet {

	private Servlet proxy;

	public void init() throws ServletException {
		super.init();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext( getServletContext() );
		proxy = (Servlet) wac.getBean( getServletName() );
		proxy.init( getServletConfig() );
	}

	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		proxy.service( arg0, arg1 );
	}

}
