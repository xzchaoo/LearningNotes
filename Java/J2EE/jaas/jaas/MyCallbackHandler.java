package org.xzc.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * Created by xzchaoo on 2016/4/14 0014.
 */
public class MyCallbackHandler implements CallbackHandler {
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback c : callbacks) {
			if (c instanceof NameCallback) {
				NameCallback nc = (NameCallback) c;
				nc.setName("xzc");
			} else if (c instanceof PasswordCallback) {
				PasswordCallback pc = (PasswordCallback) c;
				pc.setPassword("xzc7".toCharArray());
			} else throw new UnsupportedCallbackException(c);
		}
	}
}
