package org.xzc.buy.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IMyWebService {
	String sayHi(@WebParam(name = "name") String name);
}
