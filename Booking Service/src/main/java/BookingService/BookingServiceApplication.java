package BookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
This is the main class to run the Booking Service application.
 */

@SpringBootApplication
public class BookingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

    /*
    This method creates a bean of Rest Template at run time.
     */

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}