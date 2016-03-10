package xzc.service;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class TestClient {
	public static void main(String[] args) throws Exception {
		f2();
	}

	public static void f2() throws Exception {
		// 方案2
		//先到某个目录执行
		//wsimport -d . -keep -verbose http://127.0.0.1:6667/ms?wsdl
		//-p可以修改产生文件的包(即你产生的结果并不一定要按照原来作者所组织的那样 xzc.service... 可以自己指定比如abc...)
		//-keep用于指示生成java源文件
		MyServiceService mss = new MyServiceService();
		System.out.println( mss.getMyServicePort().add( 2, 4 ) );
	}

}
