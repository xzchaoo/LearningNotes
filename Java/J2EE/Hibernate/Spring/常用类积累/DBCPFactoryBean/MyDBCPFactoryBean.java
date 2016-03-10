package xzc.dbcp;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.FactoryBean;

public class MyDBCPFactoryBean implements FactoryBean<DataSource> {

	public DataSource getObject() throws Exception {
		try {
			Properties prop = new Properties();
			prop.load( MyDBCPFactoryBean.class.getClassLoader().getResourceAsStream( configPath ) );
			return BasicDataSourceFactory.createDataSource( prop );
		} catch (Exception e) {
			throw new ExceptionInInitializerError( e );
		}
	}

	public Class<? extends DataSource> getObjectType() {
		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	private String configPath = "dbcp.properties";

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
}
