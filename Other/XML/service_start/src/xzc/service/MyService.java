package xzc.service;

import javax.jws.WebService;

//SOAP Simple Object Access Protocol 使用XML来传递信息

@WebService(endpointInterface = "xzc.service.IMyService")
public class MyService implements IMyService {

	public int add(int a, int b) {
		System.out.println( a + "+" + b + "=" + ( a + b ) );
		return a + b;
	}

	public int minus(int a, int b) {
		System.out.println( a + "-" + b + "=" + ( a - b ) );
		return a - b;
	}

	@Override
	public User login(String name, String password) {
		System.out.println( "[name=" + name + ",password=" + password + "]" );
		return new User( 1, name, password );
	}

}
