Configuration
	接口 定义了最基本的get方法
FileConfiguration
	所有基于file的cfg的接口 新增了一些方法, 比如 getFile load save autoSave URL reload encoding
CombinedConfiguration CompositeConfiguration
	都是可以让多个cfg合起来当做一个用 对于他们之间的区别我一直搞不清...
	对cfg的修改最后的偶会影响它们
ConfigurationBuilder
	用于构建cfg
	目前只有DefaultConfigurationBuilder这么一个实现类
DefaultConfigurationBuilder
	本身是一个XMLConfiguration,从xml读取配置用来构造其他的cfg
ConfigurationConverter
	用于 cfg map properties 的转换
ConfigurationUtils
	克隆 追加 复制 有结构<=>无结构 dump 文件URL相关方法
DatabaseConfiguration
	从数据库读取cfg信息
DataConfiguration
	套在一个cfg上 以提供更多种类的get方法
EnvironmentConfiguration
	读取环境变量
HierarchicalConfiguration
	结构性的cfg 比如xml
	有Node的概念 可以对Node进行操作
	configurationAt
	操作起来应该比较像xml
HierarchicalINIConfiguration
	读取 .ini文件 不过与普通的.ini有一些区别
	支持#注释
	支持:赋值 a:b 跟 a=b一样
	支持多个节 最后会合并成一个节
MapConfiguration
	包装一个map用于cfg [用的是包装设计模式]
PropertiesConfigurationLayout
	PropertiesConfigurationLayout要在对pc操作之前被setLayout
	否则额没有数据
		PropertiesConfiguration pc = new PropertiesConfiguration();
		PropertiesConfigurationLayout pcl=new PropertiesConfigurationLayout( pc,null );
		pc.addProperty( "a", 1 );
		pc.addProperty( "b", 1 );
		pc.setLayout( pcl );
		pc.getLayout().setHeaderComment( "haha" );
		pc.save( System.out );
		这样是有输出的 但如果你移动pcl那行的位置 就可能会无输出
	当然pc本身已经有一个layout了 一般用那个就足够了
PropertyConverter
	用于类型转换 各种基本数据类型转换
SystemConfiguration
	系统属性配置
XMLConfiguration
	最常用的
XMLPropertiesConfiguration
	This configuration implements the XML properties format introduced in Java 5.0, see http://java.sun.com/j2se/1.5.0/docs/api/java/util/Properties.html. An XML properties file looks like this:  <?xml version="1.0"?>
	<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
	<properties>
	   <comment>Description of the property list</comment>
	   <entry key="key1">value1</entry>
	   <entry key="key2">value2</entry>
	 <entry key="key3">value3</entry>
	 </properties>
	  The Java 5.0 runtime is not required to use this class. The default encoding for this configuration format is UTF-8. Note that unlike PropertiesConfiguration, XMLPropertiesConfiguration does not support includes. Note:Configuration objects of this type can be read concurrently by multiple threads. However if one of these threads modifies the object, synchronization has to be performed manually.

org.apache.commons.configuration.beanutils包下的几个类用于从xml文件配置信息创建bean
	看下api和集合例子应该很快就懂了
StrSubstitutor
	这个是commons Lang 下的类 可以实现简单插值 ${a}
	配合ConfigurationInterpolator实现复杂一点的插值${a:b} a:b 不是简单的 "a:b"
		Map<String, String> m = new HashMap<String, String>();
		m.put( "a", "123" );
		m.put( "b", "456" );
		ConfigurationInterpolator.registerGlobalLookup( "x", StrLookup.mapLookup( m ) );
		StrLookup sl = new ConfigurationInterpolator();
		StrSubstitutor ss = new StrSubstitutor( sl, "${", "}", '$' );
		System.out.println( ss.replace( "${x:a},${x:b}" ) );
Package org.apache.commons.configuration.interpol
	包下提供一些lookup
org.apache.commons.configuration.reloading
	包下提供一些reload策略
Package org.apache.commons.configuration.web
	包下提供Servlet ServletContext ServletFilter ServletRequest的cfg
Package org.apache.commons.configuration.tree.xpath
	树 xpath 相关
JNDIConfiguration
MultiFileHierarchicalConfiguration
PatternSubtreeConfigurationWrapper
