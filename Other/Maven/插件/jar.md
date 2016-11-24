属于 archiver 系列插件的一种, 其他还有 war ejb 等


当你使用了 -jar 的时候 java 会忽略所有的 classpath, 所以你无法使用classpath来指定你的依赖...
只能根据清单文件中的 Class-Path 来指定, 这个可以由下面的插件生成

```
<plugins>
	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-jar-plugin</artifactId>
		<configuration>
			<!--
			<includes/>
			<excludes/>
			-->
			<archive>
				<manifest>
					<mainClass>app.App</mainClass>
					<addClasspath>true</addClasspath>
					<classpathPrefix>lib/</classpathPrefix>
					<addDefaultImplementationEntries>true</addDefaultImplementationEntries>
				</manifest>
				<!--定制额外的键值对-->
				<manifestEntries>
					<key1>value1</key1>
				</manifestEntries>
			</archive>
		</configuration>
	</plugin>
```

结果是这样
```
Manifest-Version: 1.0
Implementation-Title: learn-assembyly
Implementation-Version: 1.0-SNAPSHOT
Archiver-Version: Plexus Archiver
Built-By: Administrator
Implementation-Vendor-Id: com.xzchaoo
Class-Path: lib/commons-lang3-3.4.jar
Created-By: Apache Maven 3.3.9
Build-Jdk: 1.8.0_101
Main-Class: app.App
```

所以你的目录结构是:

```
/lib/*.jar 这里存放所有的依赖
/app.jar
```

这样你就可以直接 java -jar app.jar 运行了

# 杂 #
1. 这里介绍了通过占位符的方式 指定依赖存放的位置
	1. https://maven.apache.org/shared/maven-archiver/examples/classpath.html
	2. 通过这个方式可以解决war包一直被诟病的一个问题: 很难直观地知道你依赖的是谁, 因为 WEB-INF/lib 下放的是形如 fastjson-1.2.6.jar 这样 而不是全名 com.alibaba-fastjson-1.2.6.jar
	3. 这里介绍了 -SNAPSHOT 的处理
	4. 


