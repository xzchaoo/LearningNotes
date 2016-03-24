package xzc.test;

import java.util.HashSet;
import java.util.Set;

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
		for (int i = 0; i < 10; ++i) {
			Card c = new Card();
			c.setMoney( 100 );

			Student st = new Student();
			st.setName( "xzc" );
			Set<Card> set = new HashSet<Card>();
			set.add( c );
			st.setCards( set );
			c.setOwner( st );
			s.save( st );
			s.save( c );
		}
		s.clear();
		s.createQuery( "from Card" ).list().size();
		//s.createQuery( "from Card c left  join c.owner" ).list().size();
		//s.createCriteria( Card.class ).list().size();
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