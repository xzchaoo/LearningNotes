package org.xzc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.xzc.service.FirstWs;
import org.xzc.service.User;

@WebService(endpointInterface = "org.xzc.service.FirstWs", serviceName = "FirstWsNoName")
public class FirstWsImpl implements FirstWs {

	public String sayHi(String name) {
		System.out.println( "Hi " + name );
		return "Hi " + name;
	}

	@Override
	public String sayHiToUser(User user) {
		System.out.println( "Hi " + user.getName() );
		return "Hi " + user.getName();
	}

	@Override
	public List<User> getUserList() {
		System.out.println( "调用getUserList" );
		return list;
	}

	private List<User> list = new ArrayList<User>();
	private Map<String, User> map = new HashMap<String, User>();

	public FirstWsImpl() {
		list.add( new User( 1, "xzc1", 21 ) );
		list.add( new User( 2, "xzc2", 22 ) );
		list.add( new User( 3, "xzc3", 23 ) );
		for (User u : list)
			map.put( "" + u.getId(), u );
	}

	@Override
	public void sayNothing() {
		System.out.println( "sayNothing" );
	}


}
