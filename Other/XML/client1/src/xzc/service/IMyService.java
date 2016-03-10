
package xzc.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IMyService", targetNamespace = "http://service.xzc/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IMyService {


    /**
     * 
     * @param a
     * @param b
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://service.xzc/", className = "xzc.service.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://service.xzc/", className = "xzc.service.AddResponse")
    @Action(input = "http://service.xzc/IMyService/addRequest", output = "http://service.xzc/IMyService/addResponse")
    public int add(
        @WebParam(name = "a", targetNamespace = "")
        int a,
        @WebParam(name = "b", targetNamespace = "")
        int b);

    /**
     * 
     * @param a
     * @param b
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "minus", targetNamespace = "http://service.xzc/", className = "xzc.service.Minus")
    @ResponseWrapper(localName = "minusResponse", targetNamespace = "http://service.xzc/", className = "xzc.service.MinusResponse")
    @Action(input = "http://service.xzc/IMyService/minusRequest", output = "http://service.xzc/IMyService/minusResponse")
    public int minus(
        @WebParam(name = "a", targetNamespace = "")
        int a,
        @WebParam(name = "b", targetNamespace = "")
        int b);

    /**
     * 
     * @param password
     * @param name
     * @return
     *     returns xzc.service.User
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://service.xzc/", className = "xzc.service.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://service.xzc/", className = "xzc.service.LoginResponse")
    @Action(input = "http://service.xzc/IMyService/loginRequest", output = "http://service.xzc/IMyService/loginResponse")
    public User login(
        @WebParam(name = "name", targetNamespace = "")
        String name,
        @WebParam(name = "password", targetNamespace = "")
        String password);

}
