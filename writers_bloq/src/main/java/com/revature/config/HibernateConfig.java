package com.revature.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.revature.models.Chapter;
import com.revature.models.Comments;
import com.revature.models.Content;
import com.revature.models.Story;
import com.revature.models.Tag;
import com.revature.models.Token;
import com.revature.models.User;

@Configuration
public class HibernateConfig {

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		System.out.println("Configuring session factory");
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));

		// Set annotated Classes
		factoryBean.setAnnotatedClasses(User.class, Token.class, Story.class, Tag.class, Chapter.class, Content.class,
				Comments.class);
		factoryBean.setDataSource(getDataSource());
		return factoryBean;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		System.out.println("Configuring data source");
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(System.getenv("p2url"));
		dataSource.setUsername(System.getenv("p2username"));
		dataSource.setPassword(System.getenv("p2password"));
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		System.out.println("Configuring transaction manager");
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
}
