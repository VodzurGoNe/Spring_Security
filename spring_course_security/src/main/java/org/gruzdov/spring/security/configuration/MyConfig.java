package org.gruzdov.spring.security.configuration;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


@Configuration
@ComponentScan({"org.gruzdov.spring.security"})
@EnableWebMvc
@PropertySource(value = "classpath:db.property")
public class MyConfig {
    private Environment environment;

    @Autowired
    public MyConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(environment.getRequiredProperty("db.driverClassName"));
            dataSource.setJdbcUrl(environment.getRequiredProperty("db.url"));
            dataSource.setUser(environment.getRequiredProperty("db.username"));
            dataSource.setPassword(environment.getRequiredProperty("db.password"));

        } catch (PropertyVetoException var3) {
            var3.printStackTrace();
        }

        return dataSource;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver =
                new InternalResourceViewResolver();

        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;
    }
}