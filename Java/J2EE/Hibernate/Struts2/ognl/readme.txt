ognl��򵥵���Ҫһ�� expression context root
context���Ա�ʡ��
���еĲ���Ĭ�϶�����root������
	����"a+b",ctx,root
	��ô��root.getA()+root.getB()   //���root��һ��map�Ļ�(�������������� ֻҪ�ж�Ӧ��PropertyAccessor����)Ҳ�ǿ��Է��ʵ�
	ʹ��#a ǿ��ָ����context���a
	����Ognl.setValue�� #a ��Ч(��contex���ֵ��û������)

��ĳ����ָ��PropertyAccessor
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
	// �ҷ���������Ա�����forѭ��!
	// System.out.println( Ognl.getValue( "[0].{?#this>3}", list ) );

}

����ΪMyRoot������һ��accessor,�𵽸��ϵ�Ч��
struts2��ֵջ��������,��һ�����ϸ�

���ƵĻ����ԶԷ������ж��� MethodAccessor
