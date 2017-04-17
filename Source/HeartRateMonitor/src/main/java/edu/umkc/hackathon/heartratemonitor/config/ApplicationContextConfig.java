package edu.umkc.hackathon.heartratemonitor.config;

import java.util.Properties;

import javax.sql.DataSource;
 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
 
@Configuration
@ComponentScan("org.o7planning.springmvcjdbc.*")
 
@EnableTransactionManagement
 
// Load to Environment.
@PropertySources({ @PropertySource("classpath:ds/datasource-cfg.properties") })
 
public class ApplicationContextConfig {
 
   // The Environment class serves as the property holder
   // and stores all the properties loaded by the @PropertySource
   @Autowired
   private Environment env;
 
   @Bean(name = "viewResolver")
   public InternalResourceViewResolver getViewResolver() {
       InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
 
       viewResolver.setPrefix("/WEB-INF/pages/");
       viewResolver.setSuffix(".jsp");
 
       return viewResolver;
   }
 
   @Bean(name = "dataSource")
   public DataSource getDataSource() {
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
       // See: datasouce-cfg.properties
       dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
       dataSource.setUrl(env.getProperty("ds.url"));
       dataSource.setUsername(env.getProperty("ds.username"));
       dataSource.setPassword(env.getProperty("ds.password"));
 
       System.out.println("## getDataSource: " + dataSource);
 
       return dataSource;
   }
 
   @Bean(name = "transactionManager")
   public DataSourceTransactionManager getTransactionManager() {
       DataSourceTransactionManager txManager = new DataSourceTransactionManager();
 
       DataSource dataSource = this.getDataSource();
       txManager.setDataSource(dataSource);
 
       return txManager;
   }
   
   @Autowired
   @Bean(name = "sessionFactory")
   public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
       Properties properties = new Properties();
      
       // See: ds-hibernate-cfg.properties
       properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
       properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
       properties.put("current_session_context_class", env.getProperty("current_session_context_class"));
        
   
       LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
       factoryBean.setPackagesToScan(new String[] { "edu.umkc.hackathon.heartratemonitor.entity" });
       factoryBean.setDataSource(dataSource);
       factoryBean.setHibernateProperties(properties);
       factoryBean.afterPropertiesSet();
       //
       SessionFactory sf = factoryBean.getObject();
       return sf;
   }
 
}
