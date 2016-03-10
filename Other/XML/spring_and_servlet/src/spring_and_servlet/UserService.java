package spring_and_servlet;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	public void say() {
		System.out.println( "调用UserService的say方法" );
	}
}
