realm 用于存储账号密码组等信息, 这些信息将会被用于认证
默认有ManagementRealm ApplicationRealm

domain
默认有jboss-ejb-policy, jboss-web-policy, and other.

web和ejb只能直接使用domain
他们通过login module

Security domains can be configured to use Security Realms for identity information (e.g. other allows applications to specify a security realm to use for authentication and getting authorization information)

Only the core management (e.g. the management interfaces) and the EJB remoting end points can use the Security Realms directly.

