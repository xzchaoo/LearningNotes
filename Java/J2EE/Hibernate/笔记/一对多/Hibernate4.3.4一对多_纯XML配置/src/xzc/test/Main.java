package xzc.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xzc.model.Card;
import xzc.model.Student;

public class Main {
	private SessionFactory sessionFactory = new Configuration().configure( "hibernate2.cfg.xml" ).buildSessionFactory();
	private Session s;

	@Test
	public void test2() {
		Student st = new Student();
		st.setName( "xzc" );
		// Set<Card> set=new HashSet<Card>();
		Card c = new Card();
		c.setOwner( st );
		c.setMoney( 188 );
		s.save( st );
		s.save( c );
		
		s.clear();
		st=(Student)s.get( Student.class, 1 );
		System.out.println(st.getCards().size());
	}

	@Before
	public void before() {
		s = sessionFactory.openSession();
		s.beginTransaction();
	}

	@After
	public void after() {
		s.getTransaction().commit();
		s.close();
	}

}