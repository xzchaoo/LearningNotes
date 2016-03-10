package xzc.service;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class TestClient {
	public static void main(String[] args) throws Exception {

	}

	void f1() throws Exception {
		// 方案1
		URL url = new URL( "http://127.0.0.1:6667/ms" );
		QName name = new QName( "http://service.xzc/", "MyServiceService" );
		Service service = Service.create( url, name );
		// 这里需要获得 IMyService这个接口
		IMyService ims = service.getPort( IMyService.class );
		int c = ims.add( 2, 3 );
		System.out.println( "c=" + c );
	}

	void f2() throws Exception {
		// 方案2
		URL url = new URL( "http://127.0.0.1:6667/ms" );
		QName name = new QName( "http://service.xzc/", "MyServiceService" );
		Service service = Service.create( url, name );
		// 这里需要获得 IMyService这个接口
		IMyService ims = service.getPort( IMyService.class );
		int c = ims.add( 2, 3 );
		System.out.println( "c=" + c );
	}

}
