package test.classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class MyClassLoader extends ClassLoader {
	public MyClassLoader() {
		super( null );
		// super( MyClassLoader.class.getClassLoader() );
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		FileInputStream fis = null;
		byte[] classBytes = null;
		try {
			fis = new FileInputStream( "Person.class" );
			classBytes = IOUtils.toByteArray( fis );
		} catch (IOException e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (Exception ignore) {
				}
		}
		return defineClass( name, classBytes, 0, classBytes.length );
	}
}
