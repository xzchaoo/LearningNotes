package spring_and_servlet;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

@Controller("bean1")
public class MyServlet extends HttpServlet {
	@Resource
	private UserService userService;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType( "text/plain" );
		resp.setCharacterEncoding( "utf-8" );
		resp.getWriter().println( "你们好啊" );
		userService.say();
	}

}
