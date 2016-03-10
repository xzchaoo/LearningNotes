package xzc.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IMyService {
	int add(@WebParam(name = "a") int a, @WebParam(name = "b") int b);

	int minus(@WebParam(name = "a") int a, @WebParam(name = "b") int b);

	User login(@WebParam(name = "name") String name, @WebParam(name = "password") String password);
}
