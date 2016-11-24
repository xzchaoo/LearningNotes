```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-source-plugin</artifactId>
	<version>3.0.1</version>
	<executions>
		<execution>
			<id>attach-sources</id>
			<goals>
				<goal>jar-no-fork</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

# 几个常用的goal #
source:jar 达成jar包
source:jar-no-fork