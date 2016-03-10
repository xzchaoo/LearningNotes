package test.classloader;

import org.junit.Test;

public class TestClassLoader {
	@Test
	public void test2() throws Exception {
		MyClassLoader c1 = new MyClassLoader();
		MyClassLoader c2 = new MyClassLoader();
		Class cl1 = c1.loadClass( "test.classloader.Person" );
		Class cl2 = c2.loadClass( "test.classloader.Person" );
		System.out.println(cl1.getName());
		System.out.println(cl2.getName());
		Person p1 = (Person) cl1.newInstance();
		//Person p2 = (Person) cl2.newInstance();
		//System.out.println( cl1.equals( cl2 ) );
	}

	public void test_ClassLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println( Persona.class.getClassLoader() );
		ClassLoader oldcl = TestClassLoader.class.getClassLoader();

		System.out.println( oldcl );

		Class<Persona> cp = (Class<Persona>) TestClassLoader.class.getClassLoader().loadClass( "test.classloader.Person" );
		System.out.println( cp.getClassLoader() );
		Persona p1 = new Persona();
		Persona p2 = cp.newInstance();
		p1 = p2;
		p2 = p1;

	}
}
