```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-javadoc-plugin</artifactId>
	<version>2.10.4</version>
	<executions>
		<execution>
			<id>attach-javadocs</id>
			<goals>
				<goal>jar</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

# 几个常用的goal #
javadoc:javadoc 直接在 target/apidocs 下生成html文件
javadoc:jar 打成jar包 通常是发布的时候用

