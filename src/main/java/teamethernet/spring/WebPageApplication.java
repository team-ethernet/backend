package teamethernet.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"teamethernet.rest_api", "teamethernet.database"})
@EntityScan(basePackages = {"teamethernet.database"})
@EnableJpaRepositories(basePackages = {"teamethernet.rest_api", "teamethernet.database"})
public class WebPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPageApplication.class, args);
    }

}
