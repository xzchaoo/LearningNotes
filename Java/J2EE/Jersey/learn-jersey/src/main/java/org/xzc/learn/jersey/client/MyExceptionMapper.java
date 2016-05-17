package org.xzc.learn.jersey.client;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
public class MyExceptionMapper implements ExceptionMapper<Exception> {
	public Response toResponse(Exception exception) {
		System.out.println("怎么了");
		return Response.ok().build();
	}
}
