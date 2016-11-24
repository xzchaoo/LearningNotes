可以用于将项目的成果打包

比如我们现在有一个项目, 它的格式是jar, 我们想要打成如下的包

xxx.zip
```
/lib/*.jar 这里放依赖的jar包
/scripts/*.sh 这里放各种脚本
/app.jar 这是我们的可运行的jar
```

添加插件
```

<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-jar-plugin</artifactId>
	<configuration>
		<archive>
			<manifest>
				<mainClass>app.App</mainClass>
				<addClasspath>true</addClasspath>
				<classpathPrefix>lib/</classpathPrefix>
				<addDefaultImplementationEntries>true</addDefaultImplementationEntries>
			</manifest>
		</archive>
	</configuration>
</plugin>

<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-assembly-plugin</artifactId>
	<version>3.0.0</version>
	<configuration>
		<descriptors>
			<descriptor>src/assembly/assembly-zip.xml</descriptor>
		</descriptors>
	</configuration>
	<executions>
		<execution>
			<id>make-assembly</id>
			<phase>package</phase>
			<goals>
				<goal>single</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
其中 src/assembly/assembly-zip.xml 用于控制项目打包的layout
```
<?xml version="1.0" encoding="UTF-8"?>
<assembly xmlns="http://maven.apache.org/ASSEMBLY/2.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/ASSEMBLY/2.0.0 http://maven.apache.org/xsd/assembly-2.0.0.xsd">
	<id>bin</id>

	<!--打成zip-->
	<formats>
		<format>zip</format>
	</formats>

	
	<fileSets>
		<fileSet>
			<directory>${basedir}/scripts/</directory>
			<outputDirectory>/scripts/</outputDirectory>
		</fileSet>
	</fileSets>

	<!--跟上面的相比 下面的就是只对某个文件进行操作-->
	<files>
		<file>
			<source>target/${project.name}-${project.version}.jar</source>
			<outputDirectory>/</outputDirectory>
			<destName>app.jar</destName>
		</file>
	</files>

	<!--这个元素用于描述如何处理你的依赖-->
	<dependencySets>
		<!--将我的所有依赖复制到 /lib 目录-->
		<dependencySet>
			<outputDirectory>/lib</outputDirectory>
			<!--这里主要适用于排除自己, 否则当前项目的jar也会被放到lib里-->
			<excludes>
				<exclude>${project.groupId}:${project.artifactId}</exclude>
			</excludes>
		</dependencySet>
	</dependencySets>
</assembly>

```
执行 mvn clean package 就会多打出一个zip包了
