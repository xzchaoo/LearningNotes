mvn clean compile
mvn clean test

mvn clean package
	��� ���jar��
	����=artifactId-groupId.jar
		=xzc-test1-1.0-SNAPSHOT.jar

mvn clean install
	��װ�����زֿ�

ʹ�����ɵ�jar����ִ��(ָ��ĳ�����main����Ϊ��ں���)

��archetype���ɹǼ�
mvn archetype:generate
	ʵ������������maven-archetype-plugin���
	��ʽgroupId:artifactId:version:goal
	������д:

packaging:
classifier:

������Χ
	compile test provided
	runtime system

mvn dependency:list
mvn dependency:tree
mvn dependency:analyze


j2ee sdk


��ΰ�װ�����زֿ�
	����oracle��jdbc�ļ�



http://maven.apache.org/plugins/index.html

mvn help:describe -Dplugin=source


��ģ�����

/pom.xml
	���pom�ļ����ڹ���m1��m2
/m1
	ģ��1,����ͨ����Ŀһ��ֻ�Ǳ�������/m1�¶���,
	��ҲҪ���Լ���pom�ļ�
/m2
	...

�̳�
gropuId version description dependencies properties �� 
���Ա��̳�
���׵�dependencies�ᱻ�������̳�
���ǿ������������ģ�鲢��һ����Ҫʹ��ȫ��������
����ʹ��dependencyManagement�ڸ�Ԫ������:�������Ŀ�����õ�������
dependencyManagement�µ�Ԫ����һ��dependencies ���ú�ƽʱһ��
Ȼ���������ģ��������dependencies,����ֻ��ҪЩgid��aid������,��Ϊ�汾��������Ϣ(����scope)�Ѿ��ڸ�������ָ����
