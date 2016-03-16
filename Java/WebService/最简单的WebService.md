SEI (service endpoint interface) 
SIB (service implemention bean)

```
public class Main {
	public static void main(String[] args) {
		IMyService ims = new MyService();
		Endpoint.publish( "http://127.0.0.1:10779/ns", ims );
	}
}
@WebService
public interface IMyService {
	public String say(String name);
}
@WebService(endpointInterface = "org.xzc.learn.webservice1.IMyService")
public class MyService implements IMyService {
	public String say(String name) {
		return "你好, " + name;
	}
}
访问http://127.0.0.1:10779/ns?wsdl就可以看到wsdl的定义了
public class Client {
	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL( "http://127.0.0.1:10779/ns?wsdl" );
		//下面第一个参数是我们的包名倒着写
		QName name = new QName( "http://webservice1.learn.xzc.org/", "MyServiceService" );
		Service service = Service.create( url, name );
		IMyService ims = service.getPort( IMyService.class );
		String result = ims.say( "asdf" );
		System.out.println( result );
	}
}
```
