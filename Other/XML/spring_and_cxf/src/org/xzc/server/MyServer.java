package org.xzc.server;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws22.EndpointImpl;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xzc.service.impl.FirstWsImpl;

public class MyServer {
	public static void main(String[] args) {
		System.out.println( "启动服务器" );
		FirstWsImpl fwi = new FirstWsImpl();
		String address = "http://127.0.0.1:6667/ws";
		EndpointImpl e = (EndpointImpl) Endpoint.publish( address, fwi );
		e.getOutInterceptors().add( new LoggingOutInterceptor() );
		e.getInInterceptors().add( new LoggingInInterceptor() );
		
		/*e.getInInterceptors().add( new LoginInterceptor() );
		e.getInInterceptors().add( new AbstractPhaseInterceptor<SoapMessage>( Phase.PREPARE_SEND ) {
			public void handleMessage(SoapMessage message) throws Fault {
				System.out.println( "操你们大爷" );
				Document doc = DOMUtils.createDocument();
				Element name = doc.createElement( "name" );
				name.setTextContent( "xzc" );
				Element password = doc.createElement( "password" );
				password.setTextContent( "xzc" );
				Element e = doc.createElement( "auth" );
				e.appendChild( name );
				e.appendChild( password );
				message.getHeaders().add( new SoapHeader( new QName( "auth" ), e ) );
			}
		} );*/
	}
}
