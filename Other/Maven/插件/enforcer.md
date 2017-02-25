用于对项目作出强制的限制, 使得项目满足某些规范


http://maven.apache.org/enforcer/maven-enforcer-plugin/




# 配置


#
常用约束

[bannedDependencies](http://maven.apache.org/enforcer/enforcer-rules/bannedDependencies.html)
禁止某些依赖

[reactorModuleConvergence](http://maven.apache.org/enforcer/enforcer-rules/reactorModuleConvergence.html)
强制整个项目的依赖版本号是一样的

[dependencyConvergence](http://maven.apache.org/enforcer/enforcer-rules/dependencyConvergence.html)
保证项目不会依赖与不同版本的依赖






# 自定义约束

=======

# 常见配置 #
	<plugin>
		<artifactId>maven-enforcer-plugin</artifactId>
		<version>1.4.1</version>
		<executions>
			<execution>
				<id>enforce-versions</id>
				<goals>
					<goal>enforce</goal>
				</goals>
				<configuration>
					<rules>
						<requireMavenVersion>
							<version>3.0.0</version>
						</requireMavenVersion>
						<requireJavaVersion>
							<version>1.5</version>
						</requireJavaVersion>
						<requireOS>
							<family>windows</family>
						</requireOS>
					</rules>
				</configuration>
			</execution>
		</executions>
	</plugin>

# 常见规则 #

alwaysFail 总是失败
alwaysPass 总是通过

禁止某些依赖 bannedDependencies
保证依赖的版本一致

reactorModuleConvergence

