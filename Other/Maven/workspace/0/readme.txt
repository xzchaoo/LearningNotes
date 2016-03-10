mvn clean compile
mvn clean test

mvn clean package
	打包 输出jar包
	名字=artifactId-groupId.jar
		=xzc-test1-1.0-SNAPSHOT.jar

mvn clean install
	安装到本地仓库

使得生成的jar包可执行(指定某个类的main方法为入口函数)

用archetype生成骨架
mvn archetype:generate
	实际上是在运行maven-archetype-plugin插件
	格式groupId:artifactId:version:goal
	可以缩写:

packaging:
classifier:

依赖范围
	compile test provided
	runtime system

mvn dependency:list
mvn dependency:tree
mvn dependency:analyze


j2ee sdk


如何安装到本地仓库
	比如oracle的jdbc文件



http://maven.apache.org/plugins/index.html

mvn help:describe -Dplugin=source


多模块管理

/pom.xml
	这个pom文件用于管理m1和m2
/m1
	模块1,跟普通的项目一样只是被放在了/m1下而已,
	它也要有自己的pom文件
/m2
	...

继承
gropuId version description dependencies properties 等 
可以被继承
父亲的dependencies会被无条件继承
但是可能你的所有子模块并不一定都要使用全部的依赖
可以使用dependencyManagement在父元素声明:我这个项目可能用到的依赖
dependencyManagement下的元素是一个dependencies 配置和平时一样
然后在你的子模块里配置dependencies,配置只需要些gid和aid就行了,因为版本和其他信息(比如scope)已经在父亲那里指定了
