package org.xzc.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "first_ws")
public interface FirstWs {
	String sayHi(@WebParam(name = "name1") String name);

	String sayHiToUser(@WebParam(name = "user2") User user);

	List<User> getUserList();

	void sayNothing();
}
