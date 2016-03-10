
package org.xzc.services2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.xzc.services2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SayHiToUserResponse_QNAME = new QName("http://service.xzc.org/", "sayHiToUserResponse");
    private final static QName _SayHiResponse_QNAME = new QName("http://service.xzc.org/", "sayHiResponse");
    private final static QName _SayHiToUser_QNAME = new QName("http://service.xzc.org/", "sayHiToUser");
    private final static QName _GetUserListResponse_QNAME = new QName("http://service.xzc.org/", "getUserListResponse");
    private final static QName _GetUserList_QNAME = new QName("http://service.xzc.org/", "getUserList");
    private final static QName _SayHi_QNAME = new QName("http://service.xzc.org/", "sayHi");
    private final static QName _SayNothing_QNAME = new QName("http://service.xzc.org/", "sayNothing");
    private final static QName _SayNothingResponse_QNAME = new QName("http://service.xzc.org/", "sayNothingResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.xzc.services2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHiToUserResponse }
     * 
     */
    public SayHiToUserResponse createSayHiToUserResponse() {
        return new SayHiToUserResponse();
    }

    /**
     * Create an instance of {@link SayHiResponse }
     * 
     */
    public SayHiResponse createSayHiResponse() {
        return new SayHiResponse();
    }

    /**
     * Create an instance of {@link SayHiToUser }
     * 
     */
    public SayHiToUser createSayHiToUser() {
        return new SayHiToUser();
    }

    /**
     * Create an instance of {@link GetUserListResponse }
     * 
     */
    public GetUserListResponse createGetUserListResponse() {
        return new GetUserListResponse();
    }

    /**
     * Create an instance of {@link GetUserList }
     * 
     */
    public GetUserList createGetUserList() {
        return new GetUserList();
    }

    /**
     * Create an instance of {@link SayHi }
     * 
     */
    public SayHi createSayHi() {
        return new SayHi();
    }

    /**
     * Create an instance of {@link SayNothing }
     * 
     */
    public SayNothing createSayNothing() {
        return new SayNothing();
    }

    /**
     * Create an instance of {@link SayNothingResponse }
     * 
     */
    public SayNothingResponse createSayNothingResponse() {
        return new SayNothingResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHiToUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "sayHiToUserResponse")
    public JAXBElement<SayHiToUserResponse> createSayHiToUserResponse(SayHiToUserResponse value) {
        return new JAXBElement<SayHiToUserResponse>(_SayHiToUserResponse_QNAME, SayHiToUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "sayHiResponse")
    public JAXBElement<SayHiResponse> createSayHiResponse(SayHiResponse value) {
        return new JAXBElement<SayHiResponse>(_SayHiResponse_QNAME, SayHiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHiToUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "sayHiToUser")
    public JAXBElement<SayHiToUser> createSayHiToUser(SayHiToUser value) {
        return new JAXBElement<SayHiToUser>(_SayHiToUser_QNAME, SayHiToUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "getUserListResponse")
    public JAXBElement<GetUserListResponse> createGetUserListResponse(GetUserListResponse value) {
        return new JAXBElement<GetUserListResponse>(_GetUserListResponse_QNAME, GetUserListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "getUserList")
    public JAXBElement<GetUserList> createGetUserList(GetUserList value) {
        return new JAXBElement<GetUserList>(_GetUserList_QNAME, GetUserList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "sayHi")
    public JAXBElement<SayHi> createSayHi(SayHi value) {
        return new JAXBElement<SayHi>(_SayHi_QNAME, SayHi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayNothing }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "sayNothing")
    public JAXBElement<SayNothing> createSayNothing(SayNothing value) {
        return new JAXBElement<SayNothing>(_SayNothing_QNAME, SayNothing.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayNothingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xzc.org/", name = "sayNothingResponse")
    public JAXBElement<SayNothingResponse> createSayNothingResponse(SayNothingResponse value) {
        return new JAXBElement<SayNothingResponse>(_SayNothingResponse_QNAME, SayNothingResponse.class, null, value);
    }

}
