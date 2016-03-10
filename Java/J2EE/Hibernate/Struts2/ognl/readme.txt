ognl最简单的需要一个 expression context root
context可以被省略
所有的操作默认都是以root出发的
	比如"a+b",ctx,root
	那么是root.getA()+root.getB()   //如果root是一个map的话(或其他数据类型 只要有对应的PropertyAccessor就行)也是可以访问的
	使用#a 强制指定是context里的a
	另外Ognl.setValue对 #a 无效(即contex里的值你没法设置)

对某个类指定PropertyAccessor
public static class MyRoot {
	public User a;
	public User b;
}

@Test
public void test3() throws Exception {
	List<Integer> list = new ArrayList<Integer>();
	list.add( 11 );
	list.add( 33 );

	Map<String, Object> ctx = new HashMap<String, Object>();
	OgnlRuntime.setPropertyAccessor( MyRoot.class, new ObjectPropertyAccessor() {
		public Object getProperty(Map context, Object target, Object oname) throws OgnlException {
			MyRoot r = (MyRoot) target;
			return super.getProperty( context, r.a != null ? r.a : r.b, oname );
		}
	} );
	MyRoot mr = new MyRoot();
	mr.b = new User();
	mr.b.setName( "xzcb" );
	System.out.println( Ognl.getValue( "name", mr ) );
	// 我发现这个可以被当做for循环!
	// System.out.println( Ognl.getValue( "[0].{?#this>3}", list ) );

}

我们为MyRoot定制了一个accessor,起到复合的效果
struts2的值栈就是这样,有一个复合根

类似的还可以对方法进行定制 MethodAccessor
