BeanUtils
	几乎所有操作都是调用BeanUtilBean实例来完成的
	返回值都是String
	set的参数是Object
	支持类型自动转换
	支持复杂属性 比如 action.user.name
	可以访问数组 map
	常用方法
		clone copy describe get set populate

BeanUtilsBean
	构造函数需要一个 ConvertUtilsBean和PropertyUtilsBean 如果没有提供则用默认
	与BeanUtils几乎一致
	
PropertyUtils
	这个好像不会类型自动转换
	比如 setId(int n);
	那么 PropertyUtils.setProperty(new User(),"id","77");会失败
	那么 PropertyUtils.setProperty(new User(),"id",77); 会成功 new Integer(77) 也成功
	setProperty 支持 复杂属性
	setSimpleProperty 只能普通属性
	nestProperty 支持nest属性

MethodUtil
	比如有一个方法 setXXX(父类 f);
	可以自动匹配类型 但是你传的参数是 子类对象 这也是可以调用成功的
	内部有一个匹配代价机制 比如 子类对象 用作父类对象 代价是0.25 然后其他代价... 累加 最终有一个总代价
	每次都是执行总代价最少的那个
	可以获得基本数据类型的包装器,反过来也是行的
	常用的方法有
		invokeMethod(target,methodName,arg) 这个会启动自动类型匹配
		invokeExactMethod(target,methodName,arg) 这个不会启动类型自动匹配 要求精准类型
		getAccessibleMethod(Class<?> clazz, String methodName,...) 会自动设置成可访问 

Converter
	一个 转换器 的接口
	只有一个方法 <T> T convert(Class<T> type, Object value) 

AbstractArrayConverter
	提供将String转成相应List的接口

ArrayConverter

AbstractConverter
	提供 与字符串互转的接口


妈的用这个就行了, 这个支持下面所有的方式
get/setProperty 方法的话支持内嵌属性 , 比如 card.money, 相当于是 obj.getCard().getMoney()
不过一旦card==null就会引发一个异常

get/setSimpleProperty 的话就不支持内嵌属性了

获取数组的元素
System.out.println(PropertyUtils.getIndexedProperty(user, "names[0]"));
System.out.println(PropertyUtils.getIndexedProperty(user, "names", 0));

Map也是一样
System.out.println(PropertyUtils.getMappedProperty(user, "tag(aihao)"));
System.out.println(PropertyUtils.getMappedProperty(user, "tag", "aihao"));

getNestedProperty 支持内嵌表达式

DynaBean 可以用于 PropertyUtils 的第一个参数
BasicDynaBean and BasicDynaClass 可以用于构建一个动态bean
感觉应该不算是常用的功能

BeanUtils 可以:
1. 克隆一个bean, 即使没有实现Cloneable接口
2. 复制属性
3. bean -> map, 支持属性内嵌, 相当于又内嵌了一个map
4. 用map填充bean

PropertyUtils
ConvertUtils
BeanUtilsBean
PropertyUtilsBean
ConvertUtilsBean


自定义转换器
实现Converter接口, 调用 ConvertUtils


BeanComparator
这样的话就可以根据属性动态排序了
ComparatorChain 

ContextClassLoaderLocal
MethodUtils
ConstructUtils
