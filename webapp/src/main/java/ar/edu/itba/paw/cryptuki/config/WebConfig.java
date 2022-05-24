package ar.edu.itba.paw.cryptuki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableTransactionManagement
@ComponentScan({
        "ar.edu.itba.paw.cryptuki.controller",
        "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.service",
})
@EnableWebMvc
@Configuration
@PropertySource("classpath:application.properties")
public class WebConfig {

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Bean
    public DataSource dataSource(
            @Value("${database.url}") String databaseUrl,
            @Value("${database.username}") String databaseUsername,
            @Value("${database.password}") String databasePassword
    )  {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl(databaseUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);
        return ds;
    }

//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource ds){
//        final DataSourceInitializer dsi = new DataSourceInitializer();
//        dsi.setDataSource(ds);
////        dsi.setDatabasePopulator(databasePopulator());
//        return dsi;
//    }
//
//    private DatabasePopulator databasePopulator(){
//        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
//        return dbp;
//    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.displayName());
        return messageSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

//    @Bean
//    public PlatformTransactionManager transactionManager(final DataSource ds) {
//        return new DataSourceTransactionManager(ds);
//    }

    @Value("${webappBaseUrl}")
    private String webappBaseUrl;

    @Bean(name="baseUrl")
    public String baseUrl() {
        return webappBaseUrl;
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Value("${database.url}") String databaseUrl,
            @Value("${database.username}") String databaseUsername,
            @Value("${database.password}") String databasePassword
    ) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.persistence");
        factoryBean.setDataSource(dataSource(databaseUrl,databaseUsername,databasePassword));
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        // Si ponen esto en prod, hay tabla!!!
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }



}
