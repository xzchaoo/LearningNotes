package org.xzc.dbutils;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import bean.Account;
import dbcp.DBCP;

public class TestDBUtils {
	@Test
	// 增加 删除 改 类似
	public void test1() throws SQLException {
		DBCP.begin();
		Connection con = DBCP.getConnection();
		QueryRunner qr = new QueryRunner();
		Account a = new Account();
		a.setName( "n31" );
		a.setSex( "男" );
		a.setAge( 29 );
		qr.update( con, "insert into account(name,sex,age) values(?,?,?)", a.getName(), a.getSex(), a.getAge() );
		// 获得自动产生的id
		a.setId( DBCP.getGeneratedId( qr, con ) );
		System.out.println( a.getId() );
		DBCP.commit();
	}

	//批量添加
	@Test
	public void test2() throws SQLException {
		DBCP.begin();
		Connection con = DBCP.getConnection();
		QueryRunner qr = new QueryRunner();
		Object[][] os={
			{"nn1","男",31},
			{"nn2","男",32},
			{"nn3","男",33},
			{"nn4","男",34}
		};
		qr.batch( con, "insert into account(name,sex,age) values(?,?,?)",os);
		DBCP.commit();
	}

	// 查询
	@Test
	public void test3() throws SQLException {
		DBCP.begin();
		Connection con = DBCP.getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from account where id=?";
		// 结果集的第一行的数据会被用来构造一个Account
		Account a = qr.query( con, sql, new BeanHandler( Account.class ), 10 );
		assertNotNull( a );
		System.out.println( a.toString() );
		DBCP.commit();
	}

}
