Configuration
	�ӿ� �������������get����
FileConfiguration
	���л���file��cfg�Ľӿ� ������һЩ����, ���� getFile load save autoSave URL reload encoding
CombinedConfiguration CompositeConfiguration
	���ǿ����ö��cfg����������һ���� ��������֮���������һֱ�㲻��...
	��cfg���޸�����ż��Ӱ������
ConfigurationBuilder
	���ڹ���cfg
	Ŀǰֻ��DefaultConfigurationBuilder��ôһ��ʵ����
DefaultConfigurationBuilder
	������һ��XMLConfiguration,��xml��ȡ������������������cfg
ConfigurationConverter
	���� cfg map properties ��ת��
ConfigurationUtils
	��¡ ׷�� ���� �нṹ<=>�޽ṹ dump �ļ�URL��ط���
DatabaseConfiguration
	�����ݿ��ȡcfg��Ϣ
DataConfiguration
	����һ��cfg�� ���ṩ���������get����
EnvironmentConfiguration
	��ȡ��������
HierarchicalConfiguration
	�ṹ�Ե�cfg ����xml
	��Node�ĸ��� ���Զ�Node���в���
	configurationAt
	��������Ӧ�ñȽ���xml
HierarchicalINIConfiguration
	��ȡ .ini�ļ� ��������ͨ��.ini��һЩ����
	֧��#ע��
	֧��:��ֵ a:b �� a=bһ��
	֧�ֶ���� ����ϲ���һ����
MapConfiguration
	��װһ��map����cfg [�õ��ǰ�װ���ģʽ]
PropertiesConfigurationLayout
	PropertiesConfigurationLayoutҪ�ڶ�pc����֮ǰ��setLayout
	�����û������
		PropertiesConfiguration pc = new PropertiesConfiguration();
		PropertiesConfigurationLayout pcl=new PropertiesConfigurationLayout( pc,null );
		pc.addProperty( "a", 1 );
		pc.addProperty( "b", 1 );
		pc.setLayout( pcl );
		pc.getLayout().setHeaderComment( "haha" );
		pc.save( System.out );
		������������� ��������ƶ�pcl���е�λ�� �Ϳ��ܻ������
	��Ȼpc�����Ѿ���һ��layout�� һ�����Ǹ����㹻��
PropertyConverter
	��������ת�� ���ֻ�����������ת��
SystemConfiguration
	ϵͳ��������
XMLConfiguration
	��õ�
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

org.apache.commons.configuration.beanutils���µļ��������ڴ�xml�ļ�������Ϣ����bean
	����api�ͼ�������Ӧ�úܿ�Ͷ���
StrSubstitutor
	�����commons Lang �µ��� ����ʵ�ּ򵥲�ֵ ${a}
	���ConfigurationInterpolatorʵ�ָ���һ��Ĳ�ֵ${a:b} a:b ���Ǽ򵥵� "a:b"
		Map<String, String> m = new HashMap<String, String>();
		m.put( "a", "123" );
		m.put( "b", "456" );
		ConfigurationInterpolator.registerGlobalLookup( "x", StrLookup.mapLookup( m ) );
		StrLookup sl = new ConfigurationInterpolator();
		StrSubstitutor ss = new StrSubstitutor( sl, "${", "}", '$' );
		System.out.println( ss.replace( "${x:a},${x:b}" ) );
Package org.apache.commons.configuration.interpol
	�����ṩһЩlookup
org.apache.commons.configuration.reloading
	�����ṩһЩreload����
Package org.apache.commons.configuration.web
	�����ṩServlet ServletContext ServletFilter ServletRequest��cfg
Package org.apache.commons.configuration.tree.xpath
	�� xpath ���
JNDIConfiguration
MultiFileHierarchicalConfiguration
PatternSubtreeConfigurationWrapper
