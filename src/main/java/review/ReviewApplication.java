package review;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ReviewApplication {
    private static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReviewApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}

