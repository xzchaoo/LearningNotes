http://maven.apache.org/shared/maven-archiver/
http://maven.apache.org/shared/maven-archiver/examples/classpath.html

很多插件 比如 jar war 都是属于 archiver 的一种, 因此他们共享很多配置

# 常用的配置 #
1. addMavenDescriptor=true 是否要在 MATA-INF/maven 里添加你的 pom.xml 信息
2. manifest
	1. manifestEntries 用于在清单文件下添加额外的键值对
3. addClassPath=false 是否要在清单文件里添加 Class-Path 项, 如果你想要生成一个可执行的jar那么就有用, 因为一旦你使用 -jar 那么classpath 只能由清单文件的Class-Path决定, 你无法通过-classpath指定
4. classpathPrefix classpath的前缀, 默认是 "", 将它设置成 lib/ 可以将所有的依赖都放到 lib 目录下 方便管理
5. mainClass

addDefaultImplementationEntries=false
将会导致下面的信息被添加
```
Implementation-Title: ${project.name}
Implementation-Version: ${project.version}
Implementation-Vendor-Id: ${project.groupId}
Implementation-Vendor: ${project.organization.name}
Implementation-URL: ${project.url}
```

addDefaultSpecificationEntries=false
将会导致下面的信息被添加
```
Specification-Title: ${project.name}
Specification-Version: ${project.artifact.selectedVersion.majorVersion}.${project.artifact.selectedVersion.minorVersion}
Specification-Vendor: ${project.organization.name}
```

classpathLayoutType/classpathMavenRepositoryLayout/customClasspathLayout 用于控制classpath的布局


