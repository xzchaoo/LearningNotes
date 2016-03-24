package data;

import hibernate.HibernateSessionFactory;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class ComplexTest {

	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void test() {
		Session s=HibernateSessionFactory.getSession();
		s.beginTransaction();
		/*Student st=(Student)s.get(Student.class,2);
		System.out.println(st.getName());
		for(Object obj : st.getStudentAndCourses()) {
			StudentAndCourse g=(StudentAndCourse)obj;
			System.out.println(g.getId().getStudent().getName()+" "+g.getId().getCourse().getName());
		}
		Course c=(Course)s.get(Course.class, 2);
		for(Object obj : c.getStudentAndCourses()) {
			System.out.println(((StudentAndCourse)obj).getId().getStudent().getName());
		}*/
		s.getTransaction().commit();
	}
}
