package org.xzc.jaas;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

/**
 * Created by xzchaoo on 2016/4/14 0014.
 */
public class Main {
	public static void main(String[] args) throws Exception {
		Subject s = new Subject();

		Configuration c = new Configuration() {
			@Override
			public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
				Map<String, Object> options = new HashMap<>();
				options.put("attr1", "value1");
				AppConfigurationEntry ace = new AppConfigurationEntry("org.xzc.jaas.MyLoginModule", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
				return new AppConfigurationEntry[]{
					ace
				};
			}
		};

		LoginContext lc = new LoginContext("test", s, new MyCallbackHandler(), c);
		new LoginContext("123");
		lc.login();
		System.out.println(s);
	}
}
