​<?xml version="1.0"?>
​<jboss:ejb-jar xmlns:jboss="http://www.jboss.com/xml/ns/javaee" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:s="urn:security" version="3.1" impl-version="2.0">
​  <assembly-descriptor>
​    <s:security>
​      <ejb-name>*</ejb-name>
​      <s:security-domain>security-ex2</s:security-domain>
​    </s:security>
​  </assembly-descriptor>
​</jboss:ejb-jar>





SecurityManager
Java Security Policies

policytool

CodeBase 指的是代码哪里来
SignedBy 指的是代码由谁签名

Principals

Permissions

1. 
使得JBOSS EAP6启动 SecurityManager
要使用 secmgr 选项
将standalone.conf.bat里的
rem set "SECMGR=true"
修改成
set "SECMGR=true"
2. 
编写你的policy文件





1. 用keytool生成的keystore
keytool -genkeypair -alias bs2 -keyalg RSA -keystore bs2.keystore -storepass 70862045

查询keystore里都有哪些东西
keytool -list -keystore bs2.keystore

复制bs2.keystore到EAP_HOME/standalone/configuration/下
```
<connector name="https" protocol="HTTP/1.1" scheme="https" socket-binding="https" secure="true" enabled="true">
	<ssl name="https" key-alias="bs2" password="70862045" certificate-key-file="${jboss.server.config.dir}/bs2.keystore" />
</connector>
```

访问 http://localhost:8543 (默认是8443, 我端口偏移了100), 提示证书不安全, 依然信任, 然后前往

2. 用let's encrypt 生成的证书
fullchain.pem
privkey.pem
cert.pem
chain.pem
这个证书可以用于 xzchaoo.ml 域名




pem转pkcs12
openssl pkcs12 -export -in cert.pem -inkey privkey.pem -out bs2.p12 -name bs2
接着会提示你输入密码
这样会生成一个类型为pkcs12的bs2.p12文件
这个文件可以直接使用
```
<connector name="https" protocol="HTTP/1.1" scheme="https" socket-binding="https" secure="true" enabled="true">
    <ssl name="https" password="70862045" key-alias="bs2" keystore-type="pkcs12" certificate-key-file="${jboss.server.config.dir}/bs2.p12"/>
</connector>
```            

keytool -list -keystore bs2.p12 -storetype pkcs12
提示输入密码, 然后就可以查看它里面都有些啥了


将pk12转成jks
将SRC的SRC_ALIAS导入为DEST的DEST_ALIAS
keytool -importkeystore
-srckeystore SRC_FILE -srcstoretype SRC_TYPE -srcstorepass SRC_PASS -srcalias SRC_ALIAS
-destkeystore DEST_FILE -deststorepass DEST_PASS -destalias DEST_ALIAS

keytool -importkeystore -srckeystore my.p12 -srcstoretype pkcs12 -srcstorepass 70862045 -srcalias mykk -destkeystore bs2.keystore -deststorepass 70862045 -destalias mykk2


# 7. 密码保险箱 #
大爷的被坑了!
为了将配置文件里的敏感数据去掉
使用java keystore来存储

1. 创建自己的keystore
```
keytool -genseckey -alias vault -storetype jceks -keyalg AES -keysize 128 -storepass 70862045 -keypass 70862045 -validity 730 -keystore vault.keystore
```
2. 初始化 password vault

KEYSTORE_URL 你的keystore的位置
KEYSTORE_PASSWORD
SALT 加盐
KEYSTORE_ALIAS
ITERATION_COUNT 加密几次
ENC_FILE_DIR 存储加密文件的位置

VAULT_BLOCK
ATTRIBUTE
SEC-ATTR 要存储的安全属性

7758258
77


3. 配置EAP使用password vault
4. 存储敏感字符串到password vault中

/core-service=vault:add(vault-options=[("KEYSTORE_URL" => "D:/EAP-6.4.0/vault/vault.keystore"), ("KEYSTORE_PASSWORD" => "70862045"), ("KEYSTORE_ALIAS" => "vault"), ("SALT" => "77582588"),("ITERATION_COUNT" => "77"), ("ENC_FILE_DIR" => "D:/EAP-6.4.0/vault/")])

/core-service=vault:add(vault-options=[("KEYSTORE_URL" => "PATH_TO_KEYSTORE"), ("KEYSTORE_PASSWORD" => "MASKED_PASSWORD"), ("KEYSTORE_ALIAS" => "ALIAS"), ("SALT" => "SALT"),("ITERATION_COUNT" => "ITERATION_COUNT"), ("ENC_FILE_DIR" => "ENC_FILE_DIR")])


交互式
非交互式
EAP_HOME/bin/vault.sh --keystore KEYSTORE_URL --keystore-password KEYSTORE_PASSWORD --alias KEYSTORE_ALIAS --vault-block VAULT_BLOCK --attribute ATTRIBUTE --sec-attr SEC-ATTR --enc-dir ENC_FILE_DIR --iteration ITERATION_COUNT --salt SALT


# 8. #
You secure access to EJBs and web components in an enterprise application by using the ejb-jar.xml and web.xml deployment descriptors.

security-role-ref

security-role可以出现在 web.xml ejb-jar.xml和jboss-ejb3.xml的<assembly-descriptor>元素下
jboss-web.xml 似乎有更多的选项!?


# 11 #
required 必须验证
requisite 必须成功
sufficient 一旦成功了就算成功了
optional 不要求成功

Principal 基本上就是表示了用户的一种身份
Group 扩展 Principal, 增加了对组员的增删改查的方法

SimplePrincipal 只有一一个名字
SimpleGroup 简单实现, 保证组员不重复, 由于组里面的成员可以是一个组, 所以在方法 isMember 的时候要递归判断 (似乎对SimplePrincipal有特殊处理)



# LoginContext #
void initialize(Subject subject, CallbackHandler callbackHandler, Map<String,?> sharedState, Map<String,?> options);

boolean login();
当需要认证的时候调用它, 返回认证是否成功

boolean commit();
当总体上认证通过了之后,就会调用这个方法, 一般来这里给用户添加各种principal.

boolean abort();
当总体上认证失败了之后,就会调用这个方法,

boolean logout();
注销

# AbstractServerLoginModule #
jboss提供的一个基类,
子类一般要重写 login(), getRoleSets() and getIdentity() 功能
如果你重写login, 那么最后一行一定是 return super.login();
如果你重写initialize()那么记得调用父类方法
它有如下功能
1.  password-stacking http://docs.jboss.org/jbossas/docs/Server_Configuration_Guide/4/html/Using_JBoss_Login_Modules-Password_Stacking.html
	1.  要么不设置, 要么设置为useFirstPass
	2.  意思是当你将多个login-module串起来的时候, 每个login-module会先再 shared state map里查找:
		1.  javax.security.auth.login.name 和 javax.security.auth.login.password 两个属性
		2.  一找两者都找到, 那么就认为这个用户已经登陆成功, 因此自己就不用再认证了
	3.  
2.  定制 principalClass
3.  unauthenticatedIdentity 匿名用户?


```
void initialize(Subject subject, CallbackHandler callbackHandler, Map<String,?> sharedState, Map<String,?> options){
	1. 将传进来的参数都保存起来
	2. 解析各个参数, useFirstPass, 匿名用户, 定制principal类名
}
boolean login(){
	1. loginOk=false;//标记
	2. if(useFirstPass == true)
		1. 从sharedState里寻找账号和密码
		2. 如果都找到了: loginOk=true;return;
	3. return false;//登陆失败 因为这是个抽象类, 没有那么多登陆的逻辑
}
boolean commit(){
	1. if(!loginOk)return false;
	2. 调用 getIdentity() 往Subject的principal集合里加入一个 identity(类似于 primary principal的东西)
	3. 调用 getRoleSets() 获取这个对象对应的组(Group接口), 子类应该至少保证有一个组 Roles
	4. for(每一个组g)
		1. 将用户加入到组里面, 体现为用户的principals里多了一个group, 当然要记得保证不要重复(通过遍历principal比对group的名字, gruop也是一种principal)
	5. 有点复杂
}
boolean logout(){
	从principal里删除identity和callerGroup
	return true;
}
```
引入了几个抽象方法:
```
Principal getIdentity();
Group[] getRoleSets(); 至少有一个组名为Roles  A second common group is "CallerPrincipal" that provides the application identity of the user rather than the security domain identity.

```

IdentityLoginModule
这是一个很简单的实现, 有助于了解代码.

# UsernamePasswordLoginModule #
基于账号和密码的登录模块
1. 哈希算法, 哈希编码 哈希字符集
2. 是否对服务端密码进行哈希, 是否对客户端密码进行哈希
3. 输入验证器
4. 杂 digestCallback storeDigestCallback ignorePasswordCase legacyCreatePasswordHash throwValidateError inputValidator passwordIsA1Hash

初始化方法如往常, 主要是将参数拿到手

```
boolean login(){
	if(super.login()){
		根据策略, 这一般是已经有login-module登录成功了
		初始化自己的 identity 和 credential
		return true;
	}else{
		loginOk=false;//标记
		是否有输入验证器, 有的话就调用它, 如果验证失败的话就扔异常了
		如果账号和密码都为null, 那么使用 unauthenticatedIdentity(如果它也非空的话)
		if(identity==null){
			创建identity
			可选:对用户输入密码进行哈希操作
			调用抽象方法 getUsersPassword() 获得一个期待的正确的密码
			可选:对上一步取出来的密码哈希
			调用 validatePassword 对两个密码进行比较(这个方法可能会考虑大小写问题, 然而并没有什么用)
			验证失败的话就扔出异常
			到这就是成功了, 根据 useFirstPass ,将用户名和密码(用户输入的原始密码)房到sharedState里
		}
	}
}
```
引入了一个抽象方法String getUsersPassword();



<security-domain name="test" cache-type="default">
    <authentication>
        <login-module module="deployment.ejb3.ear.ejb3-login.jar" code="org.xzc.ejb.ejb3.login.MyLoginModule" flag="optional">
            <module-option name="password-stacking" value="useFirstPass"/>
        </login-module>
    </authentication>
</security-domain>
