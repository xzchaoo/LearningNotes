BeanUtils
	�������в������ǵ���BeanUtilBeanʵ������ɵ�
	����ֵ����String
	set�Ĳ�����Object
	֧�������Զ�ת��
	֧�ָ������� ���� action.user.name
	���Է������� map
	���÷���
		clone copy describe get set populate

BeanUtilsBean
	���캯����Ҫһ�� ConvertUtilsBean��PropertyUtilsBean ���û���ṩ����Ĭ��
	��BeanUtils����һ��
	
PropertyUtils
	������񲻻������Զ�ת��
	���� setId(int n);
	��ô PropertyUtils.setProperty(new User(),"id","77");��ʧ��
	��ô PropertyUtils.setProperty(new User(),"id",77); ��ɹ� new Integer(77) Ҳ�ɹ�
	setProperty ֧�� ��������
	setSimpleProperty ֻ����ͨ����
	nestProperty ֧��nest����

MethodUtil
	������һ������ setXXX(���� f);
	�����Զ�ƥ������ �����㴫�Ĳ����� ������� ��Ҳ�ǿ��Ե��óɹ���
	�ڲ���һ��ƥ����ۻ��� ���� ������� ����������� ������0.25 Ȼ����������... �ۼ� ������һ���ܴ���
	ÿ�ζ���ִ���ܴ������ٵ��Ǹ�
	���Ի�û����������͵İ�װ��,������Ҳ���е�
	���õķ�����
		invokeMethod(target,methodName,arg) ����������Զ�����ƥ��
		invokeExactMethod(target,methodName,arg) ����������������Զ�ƥ�� Ҫ��׼����
		getAccessibleMethod(Class<?> clazz, String methodName,...) ���Զ����óɿɷ��� 

Converter
	һ�� ת���� �Ľӿ�
	ֻ��һ������ <T> T convert(Class<T> type, Object value) 

AbstractArrayConverter
	�ṩ��Stringת����ӦList�Ľӿ�

ArrayConverter

AbstractConverter
	�ṩ ���ַ�����ת�Ľӿ�


��������������, ���֧���������еķ�ʽ
get/setProperty �����Ļ�֧����Ƕ���� , ���� card.money, �൱���� obj.getCard().getMoney()
����һ��card==null�ͻ�����һ���쳣

get/setSimpleProperty �Ļ��Ͳ�֧����Ƕ������

��ȡ�����Ԫ��
System.out.println(PropertyUtils.getIndexedProperty(user, "names[0]"));
System.out.println(PropertyUtils.getIndexedProperty(user, "names", 0));

MapҲ��һ��
System.out.println(PropertyUtils.getMappedProperty(user, "tag(aihao)"));
System.out.println(PropertyUtils.getMappedProperty(user, "tag", "aihao"));

getNestedProperty ֧����Ƕ���ʽ

DynaBean �������� PropertyUtils �ĵ�һ������
BasicDynaBean and BasicDynaClass �������ڹ���һ����̬bean
�о�Ӧ�ò����ǳ��õĹ���

BeanUtils ����:
1. ��¡һ��bean, ��ʹû��ʵ��Cloneable�ӿ�
2. ��������
3. bean -> map, ֧��������Ƕ, �൱������Ƕ��һ��map
4. ��map���bean

PropertyUtils
ConvertUtils
BeanUtilsBean
PropertyUtilsBean
ConvertUtilsBean


�Զ���ת����
ʵ��Converter�ӿ�, ���� ConvertUtils


BeanComparator
�����Ļ��Ϳ��Ը������Զ�̬������
ComparatorChain 

ContextClassLoaderLocal
MethodUtils
ConstructUtils
