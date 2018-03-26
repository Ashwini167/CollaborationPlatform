package com.niit.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.niit.dao.*;
import com.niit.model.*;

@Configuration
@ComponentScan("com.niit")
@EnableTransactionManagement
public class DBConfig {
	
	static {
		System.out.println("Inside DB Config");
	}
		
		/* Method used to set the parameters for Oracle connectivity */
		@Bean(name="dataSource")
		public DataSource getOracleDataSource() {
			DriverManagerDataSource dataSource=new DriverManagerDataSource();
			dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
			dataSource.setUsername("COLLAB");
			dataSource.setPassword("pass");
			System.out.println("---Data Source Created---");
			return dataSource;
		}
		
		/* Method to create the bean of SessionFactory with all model classes in session */
		@Bean(name="sessionFactory")
		public SessionFactory getSessionFactory() {
			Properties hibernateProp=new Properties();			
			hibernateProp.setProperty("hibernate.hbm2ddl.auto", "update");
			hibernateProp.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
			hibernateProp.setProperty("hibernate.show_sql", "true");			
			LocalSessionFactoryBuilder factoryBuilder=new LocalSessionFactoryBuilder(getOracleDataSource());
			
			factoryBuilder.addAnnotatedClass(Blog.class);
			factoryBuilder.addAnnotatedClass(UserDetail.class);
			factoryBuilder.addAnnotatedClass(BlogComment.class);
			factoryBuilder.addAnnotatedClass(Forum.class);
			factoryBuilder.addAnnotatedClass(ForumComment.class);
			factoryBuilder.addAnnotatedClass(Job.class);
			factoryBuilder.addAnnotatedClass(ApplyJob.class);
			factoryBuilder.addAnnotatedClass(Friend.class);
			
			factoryBuilder.addProperties(hibernateProp);			
			System.out.println("Creating SessionFactory Bean");
			return factoryBuilder.buildSessionFactory();
		}
		
		/* Method to create the bean of TransactionManager */
		@Bean(name="txManager")
		public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
			System.out.println("---Transaction Manager----");
			return new HibernateTransactionManager(sessionFactory);
		}
		
		/* Method to create the bean of BlogDAO */
		@Bean(name="blogDAO")
		public BlogDAO getBlogDAO()	{
			System.out.println("----BlogDAO bean creation---");
			return new BlogDAOImpl();
		}
		
		/* Method to create the bean of UserDetailDAO */
		@Bean(name="userDetailDAO")
		public UserDetailDAO getUserDetailDAO()	{
			System.out.println("----UserDetailDAO bean creation---");
			return new UserDetailDAOImpl();
		}
		
		/* Method to create the bean of ForumDAO */
		@Bean(name="forumDAO")
		public ForumDAO getForumDAO()	{
			System.out.println("----ForumDAO bean creation---");
			return new ForumDAOImpl();
		}
		
		/* Method to create the bean of JobDAO */
		@Bean(name="jobDAO")
		public JobDAO getJobDAO()	{
			System.out.println("----JobDAO bean creation---");
			return new JobDAOImpl();
		}
		
		/* Method to create the bean of FriendDAO */
		@Bean(name="friendDAO")
		public FriendDAO getFriendDAO()	{
			System.out.println("----FriendDAO bean creation---");
			return new FriendDAOImpl();
		}
}