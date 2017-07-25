https://github.com/kohsuke/winsw

Java Service Wrapper

默认的sc命令貌似只能用于执行后台程序, 如果你的程序是一个前台程序貌似是不行的
当你启动程序的时候, 当前线程就会被阻塞

创建服务
sc create consul-server binPath= "J:\temp\consul.exe agent" "-config-dir=J:\temp\c1"
sc delete consul-server

http://rozanski.org.uk/services


# WinSW  #
下载 winse 放入到 PATH 下, 比如直接放到 system32 下
运行的时候可能会提示缺少.net框架 安装就行

install
uninstall
start
stop
restart
status

# 配置文件 #

    <service>
      <id>jenkins</id>
      <name>Jenkins</name>
      <description>This service runs Jenkins continuous integration system.</description>
      <env name="JENKINS_HOME" value="%BASE%"/>
      <executable>java</executable>
      <arguments>-Xrs -Xmx256m -jar "%BASE%\jenkins.war" --httpPort=8080</arguments>
      <logmode>rotate</logmode>
    </service>

