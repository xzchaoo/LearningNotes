package dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;

public class DBCP {
	private static final DataSource ds;
	static {
		try {
			Properties prop = new Properties();
			prop.load( DBCP.class.getClassLoader().getResourceAsStream( "dbcp.properties" ) );
			ds = BasicDataSourceFactory.createDataSource( prop );
		} catch (Exception e) {
			throw new ExceptionInInitializerError( e );
		}
	}

	public static DataSource getDataSource() {
		return ds;
	}

	public static Connection getConnection() throws SQLException {
		try {
			Connection con = cons.get();
			if (con == null) {
				con = ds.getConnection();
				// cons.set( con );
				// con.setAutoCommit( false );
			}
			return con;
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	private static ThreadLocal<Connection> cons = new ThreadLocal<Connection>();

	public static void begin() {
		try {
			Connection con = cons.get();
			if (con == null) {
				con = ds.getConnection();
				cons.set( con );
				con.setAutoCommit( false );
			}
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	public static void commit() {
		try {
			Connection con = cons.get();
			if (con != null) {
				con.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	public static void close() {
		try {
			Connection con = cons.get();
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			throw new RuntimeException( e );
		} finally {
			cons.remove();
		}
	}

	@Deprecated
	public static void release(Connection con, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			con = null;
		}
	}

	public static int getGeneratedId(QueryRunner qr, Connection con) throws SQLException {
		String sql = "select last_insert_id();";
		Object[] os = qr.query( con, sql, new ArrayHandler() );
		if (os == null || os.length != 1 || !( os[0] instanceof Long )) {
			throw new RuntimeException( "没有获得生成的主键" );
		}
		return ( (Long) os[0] ).intValue();
	}
}
