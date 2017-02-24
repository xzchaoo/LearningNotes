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

