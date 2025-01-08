package rubberduck.org.sportinksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "rubberduck.org.sportinksystem"
        }
)
public class SportInkSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportInkSystemApplication.class, args);
    }

}
