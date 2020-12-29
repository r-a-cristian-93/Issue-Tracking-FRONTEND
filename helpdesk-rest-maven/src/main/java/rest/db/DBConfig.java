package rest.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

import static rest.ApplicationConstants.*;

@Configuration
public class DBConfig {	
	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
				.driverClassName(DB_DRIVER)
				.url(DB_URL)
				.username(DB_USERNAME)
				.password(DB_PASSWORD)
				.build();
	}
}	
