package xzc.c3p0.factorybean;

import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//自动从指定的配置文件里读取配置
//简便了C3P0在xml文件中的配置
public class C3P0FactoryBean implements FactoryBean<ComboPooledDataSource>, InitializingBean, ResourceLoaderAware, DisposableBean {
	// 默认的配置文件名字
	private static final String DEFAULT_CONFIG_FILENAME = "c3p0.properties";
	// 配置文件名
	private String configFileName = DEFAULT_CONFIG_FILENAME;
	// 资源spring加载器
	private ResourceLoader resourceLoader;
	// 包装的对象
	private ComboPooledDataSource dataSource;

	public ComboPooledDataSource getObject() throws Exception {
		return dataSource;
	}

	public Class<? extends ComboPooledDataSource> getObjectType() {
		return ComboPooledDataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	// 利用BeanUtils注值
	// 利用resourceLoader加载文件
	@Override
	public void afterPropertiesSet() throws Exception {
		dataSource = new ComboPooledDataSource();
		Properties p = new Properties();
		p.load( resourceLoader.getResource( configFileName ).getInputStream() );
		BeanUtils.populate( dataSource, p );
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void destroy() throws Exception {
		dataSource.close();
	}

}
