package ar.edu.itba.paw.cryptuki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@ComponentScan({
        "ar.edu.itba.paw.cryptuki.controller",
        "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.service",
})
@EnableWebMvc
@Configuration
public class WebConfig {

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Bean /*Use WebMVC **BUT** use this particular view resolver*/
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public DataSource dataSource(){
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:"+System.getenv("DB_CONNECTION") + System.getenv("DB_NAME"));
        ds.setUsername(System.getenv("DB_USER"));
        ds.setPassword(System.getenv("DB_PASS"));
        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds){
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        return dsi;
    }

    private DatabasePopulator databasePopulator(){
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(schemaSql);
        return dbp;
    }
}
