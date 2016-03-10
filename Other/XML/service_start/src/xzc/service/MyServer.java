package xzc.service;

import javax.xml.ws.Endpoint;

public class MyServer {
	// SEI(Service Endpoint Interface)
	// SIB(Service Implemention Bean)

	public static void main(String[] args) {
		// 建立并启动我们的服务
		String address = "http://127.0.0.1:6667/ms";
		Endpoint.publish( address, new MyService() );
	}
}
