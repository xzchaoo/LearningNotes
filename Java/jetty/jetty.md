 HTTP server, HTTP client, and javax.servlet container.

# 目录结构 #

# 概念 #
Jetty Home jetty的家目录 属性 jetty.home
Jetty Base jetty项目的根目录, 方便与Home分开 jetty.base


# 启动 #
java -jar $JETTY_HOME/start.jar
java -jar $JETTY_HOME/start.jar --list-modules

java -jar $JETTY_HOME/start.jar --add-to-start=https,http2 添加https和http2连接器, 这句话执行完之后就自动结束了

修改端口
java -jar $JETTY_HOME/start.jar jetty.http.port=8081
java -Djetty.http.port=8081 -jar $JETTY_HOME/start.jar
也可以在 start.in 里修改

jetty.ssl.port

--module=http 表示激活http模块
观察 $JETTY_BASE/start.in 里面已经有一句话 --module=http 了

# 部署 #
webapps/ 下面的结构映射和tomcat的差不多
ROOT
test.war
test.xml jetty特有, 可以用于覆盖一些配置, 比如上下文地址, web.xml内容 Servlet初始化参数
具有一定格式 看这里 http://www.eclipse.org/jetty/documentation/9.4.1.v20170120/quickstart-config-what.html 的 Web Application Deployment 小节
test.d/ 里面可以存放重写test.xml的文件

以下的文件或目录会被忽略:
.开头的文件
.d结尾的目录
如果同时存在 foo/ foo.war 那么foo.war会被部署 foo/被忽略
foo/ foo.xml 同时存在, foo/被忽略, foo.xml 会生效
foo.war foo.xml foo.war被忽略 foo.xml会生效


# 配置 #
jetty使用POJO来配置服务器的行为
观察jetty的xml配置文件, 可以发现它其实想要体现出一个方法调用的思想
http://www.eclipse.org/jetty/documentation/9.4.1.v20170120/quick-start-configure.html

$JETTY_BASE/start.in 可以定制参数 注意是 BASE 目录下的不是HOME

$JETTY_HOME/modules/*.mod 配置每个模块的文件, 一般不会去修改它
可以通过 $JETTY_BASE/modules/*.mod 来修改配置

$JETTY_HOME/etc/*.xml 通常也是很少修改它 因为它支持占位符的方式 会读取ini里的值

每个项目的 WEB-INF/web.xml

## 配置服务器 ##
线程池 在 start.ini 里设置

### 上下文 ###
contextPath
virtualHost

### 连接器 ###
port host timeout ssl http2



普通应用的配置根元素是 <Configure class="org.eclipse.jetty.webapp.WebAppContext"/>
http://www.eclipse.org/jetty/documentation/9.4.1.v20170120/configuring-specific-webapp-deployment.html
需要配置 war contextPath
是否解压war servlet容器初始化参数 覆盖web.xml配置
配置jndi

# 处理静态资源 #

