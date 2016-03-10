package org.xzc.server;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

public class LoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	public void handleMessage(SoapMessage message) throws Fault {
		try {
			SoapHeader h = (SoapHeader) message.getHeader( new QName( "auth" ) );
			Element e = (Element) h.getObject();
			if ("auth".equals( e.getTagName() )) {
				String name = e.getElementsByTagName( "name" ).item( 0 ).getTextContent();
				String password = e.getElementsByTagName( "password" ).item( 0 ).getTextContent();
				if ("xzc".equals( name ) && "xzc".equals( password ))
					return;
			}
		} catch (Exception e) {
			throw new Fault( new SOAPException( "SOAP消息头不对" ) );
		}
	}

	public LoginInterceptor() {
		super( Phase.PRE_INVOKE );
	}
}
