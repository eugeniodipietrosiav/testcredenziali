//package com.autenticazione.testcredenziali;
//
//
//import com.autenticazione.testcredenziali.model.MessageObserver;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//public class JpaConfig {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em
//                = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan("com.example.demo.model");
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(additionalProperties());
//
//        return em;
//    }
//
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost/testdb");
//        dataSource.setUsername( "postgres" );
//        dataSource.setPassword( "password" );
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(
//            EntityManagerFactory emf){
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//
//        return transactionManager;
//    }
//
//
//
//    Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        properties.setProperty(
//                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//
//        return properties;
//    }
//}
