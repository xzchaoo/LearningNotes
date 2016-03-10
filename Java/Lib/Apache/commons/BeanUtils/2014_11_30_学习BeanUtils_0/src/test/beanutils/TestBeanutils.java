package test.beanutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaList;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.junit.Before;
import org.junit.Test;

import test.beanutils.domain.User;

public class TestBeanutils {
	private User u1, u2, u3;

	@Before
	public void before() {
		u1 = new User( 1, "xzc1", 11 );
		u1.setIntArray( new int[] { 1, 3, 9 } );

		Map<String, String> map = new HashMap<String, String>();
		map.put( "k1", "v1" );
		map.put( "k2", "v4" );
		map.put( "k3", "v9" );
		u1.setMap( map );

		u2 = new User( 2, "xzc2", 22 );
		u3 = new User();
	}

	@Test
	public void test_ArrayConverter() {
		ArrayConverter ac = new ArrayConverter( int[].class, new IntegerConverter() );
		int usar=2;
		usar=usar+2;
		System.out.println( ((int[]) ac.convert( int[].class, "1,2,3" ))[2] );
	}

	public void test_LazyDynaList() {
		// WrapDynaBean将一个bean包装成dynabean
		// LazyDynaMap简单
		// LazyDynaList ldl = new LazyDynaList( WrapDynaClass.createDynaClass( User.class ) );
		LazyDynaList ldl = new LazyDynaList( User.class );
		ldl.add( u1 );
		System.out.println( ldl.get( 0 ) );
	}

	public void test_LazyDynaBean() {
		// lazy可以在创建后动态添加删除属性
		// LazyDynaClass的使用很简单 看看就行
		LazyDynaBean ldb = new LazyDynaBean();
		ldb.set( "name", "xzc" );
		assertNotNull( ldb.get( "name" ) );
		assertEquals( "xzc", ldb.get( "name" ) );
		// ldb.set( "habits", String[].class );
		ldb.set( "habits", 0, "我是张全蛋" );
		System.out.println( ldb.get( "habits" ) );
		ldb.set( "habits", 1, "英文名是michael jack" );
		System.out.println( ldb.get( "habits" ) );
		ldb.getMap().put( "name", "233" );
		System.out.println( ldb.get( "name" ) );
	}

	public void test_ConvertUtils() {
		assertEquals( u1.toString(), ConvertUtils.convert( u1 ) );
		assertEquals( 3.34, ConvertUtils.convert( "3.34", Double.class ) );
		assertEquals( -5.333, ConvertUtils.convert( "-5.333", double.class ) );
		int[] intArray = (int[]) ConvertUtils.convert( new String[] { "1", "2", "4" }, int.class );
		// intArray是 1 2 4

		// 注册自己的转换器
		// 不过这个转换器需要负责很多东西!
		// 以Integer为例
		// 它需要负责各种类型转换到Integer
		// 所以我保存了旧的转换器
		ConvertUtils.register( new Converter() {
			private Converter defaultIntegerConverter = ConvertUtils.lookup( Integer.class );

			public <T> T convert(Class<T> type, Object value) {
				if (value.getClass() != User.class)
					return defaultIntegerConverter == null ? null : defaultIntegerConverter.convert( type, value );
				return (T) (Integer) ( (User) value ).getId();
			}
		}, Integer.class );

		Object o = ConvertUtils.convert( u1, Integer.class );
		System.out.println( o );
	}

	public void test_ConstructorUtils() {
		try {
			Object o = ConstructorUtils.invokeConstructor( User.class, new Object[] { 1, "xo", 11 },
					new Class[] { int.class, String.class, int.class } );
			System.out.println( o );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test_BeanUtils() {
		// BeanUtils与PropertyUtils最大的区别是 前者 的大部分函数返回值是String 而 后者是Object
		// BeanUtils.cloneBean( bean )
		// BeanUtils.copyProperties( dest, orig );
		// BeanUtils.populate( bean, properties ); 将properties(是个map)注入到bean里
	}

	public void test_BeanMap() {
		// BeanMap不是一个普通的map
		// 它需要以一个Object为载体
		// 对BeanMap的操作(get put) 都会映射到Object的get set方法
		BeanMap bm = new BeanMap( u1 );
		assertEquals( "xzc1", bm.get( "name" ) );
		bm.put( "name", "xzc" );
		assertEquals( "xzc", u1.getName() );
		assertEquals( "xzc", bm.get( "name" ) );
	}

	public void test_BeanComparator() {
		BeanComparator<DynaBean> c = new BeanComparator<DynaBean>( "name" );
		BasicDynaClass bdc = new BasicDynaClass( "随意的名字", null, new DynaProperty[] { new DynaProperty( "id", int.class ),
				new DynaProperty( "name", String.class ), new DynaProperty( "age", int.class ), } );
		BasicDynaBean b1 = new BasicDynaBean( bdc );
		BasicDynaBean b2 = new BasicDynaBean( bdc );
		b1.set( "name", "n1" );
		b2.set( "name", "n2" );
		int result = c.compare( b1, b2 );
		assertEquals( -1, result );
	}

	public void test_BasicDynaBean_BasicDynaClass() {
		BasicDynaBean bdb = new BasicDynaBean( new BasicDynaClass( "随意的名字", null, new DynaProperty[] { new DynaProperty( "id", int.class ),
				new DynaProperty( "name", String.class ), new DynaProperty( "age", int.class ), } ) );
		bdb.set( "name", "xzc" );
		assertEquals( "xzc", bdb.get( "name" ) );
	}

	public void test_PropertyUtils_usage() throws Exception {
		// 将u1的数据复制到u3的数据里
		assertNotEquals( u1, u3 );
		PropertyUtils.copyProperties( u3, u1 );
		assertEquals( u1, u3 );

		// 获得 <属性名,属性> 的map 这里也会获得class属性
		Map<String, Object> m = PropertyUtils.describe( u1 );
		assertNotNull( m );
		for (Entry<String, Object> e : m.entrySet()) {
			System.out.println( e.getKey() + " " + e.getValue() );
		}

		assertEquals( u1.getIntArray()[0], PropertyUtils.getIndexedProperty( u1, "intArray", 0 ) );
		assertEquals( u1.getIntArray()[0], PropertyUtils.getIndexedProperty( u1, "intArray[0]" ) );

		assertEquals( u1.getMap().get( "k1" ), PropertyUtils.getMappedProperty( u1, "map", "k1" ) );
		// 这里只能用圆括号 不能用其他 可以跟进去源代码看一下
		assertEquals( u1.getMap().get( "k1" ), PropertyUtils.getMappedProperty( u1, "map(k1)" ) );

		// 可以获得内嵌属性
		assertEquals( String.class, PropertyUtils.getNestedProperty( u1, "name.class" ) );

		assertEquals( String.class, PropertyUtils.getPropertyType( u1, "name" ) );
		assertEquals( Class.class, PropertyUtils.getPropertyType( u1, "name.class" ) );

		// simple不能有内嵌属性
		assertEquals( "xzc1", PropertyUtils.getSimpleProperty( u1, "name" ) );

		// PropertyUtils.isReadable和 isWrittable 判断可读可写
		// PropertyUtils.getProperty()跟getNestedProperty是一模一样的
		// 是前面所有get方法的汇总
		System.out.println( PropertyUtils.getNestedProperty( u1, "intArray[0]" ) );

	}
}
