https://hub.docker.com/_/tomcat/

tomcat:9-alpine
含有jdk 1.8

这是默认的配置
```
CATALINA_BASE:   /usr/local/tomcat
CATALINA_HOME:   /usr/local/tomcat
CATALINA_TMPDIR: /usr/local/tomcat/temp
JRE_HOME:        /usr
CLASSPATH:       /usr/local/tomcat/bin/bootstrap.jar:/usr/local/tomcat/bin/tomcat-juli.jar
```

docker run --rm -dP -v /opt/demo1:/usr/local/tomcat/webapps tomcat:9-alpine

将项目打成war包放到
/opt/demo1/ROOT.war

将/opt/demo1 目录挂到 /usr/local/tomcat/webapps

开启一个终端
docker exec -it b05fa4bd44ae sh
