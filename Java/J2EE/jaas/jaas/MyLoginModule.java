package org.xzc.jaas;

import com.sun.security.auth.UserPrincipal;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * Created by xzchaoo on 2016/4/14 0014.
 */
public class MyLoginModule implements LoginModule {
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	private String username;
	private String password;
	private Set<Principal> userPrincipals;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
	}

	@Override
	public boolean login() throws LoginException {

		NameCallback nc = new NameCallback("请输入用户名");
		PasswordCallback pc = new PasswordCallback("请输入密码", false);

		try {
			callbackHandler.handle(new Callback[]{nc, pc});
		} catch (IOException | UnsupportedCallbackException e) {
			LoginException le = new LoginException();
			le.initCause(e);
			throw le;
		}
		username = nc.getName();
		password = new String(pc.getPassword());
		return processWithUsernameAndPassword(username, password);
	}

	protected boolean processWithUsernameAndPassword(String username, String password) throws LoginException {
		return "xzc".equals(username) && "xzc7".equals(password);
	}

	@Override
	public boolean commit() throws LoginException {
		Set<Principal> set = subject.getPrincipals();
		userPrincipals = new LinkedHashSet<>();
		userPrincipals.add(new UserPrincipal(username));
		userPrincipals.add(new RolePrincipal("admin"));
		userPrincipals.add(new RolePrincipal("user"));
		set.addAll(userPrincipals);
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().removeAll(userPrincipals);
		return true;
	}
}
