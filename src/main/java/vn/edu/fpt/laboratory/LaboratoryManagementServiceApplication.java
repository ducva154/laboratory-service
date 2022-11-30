package vn.edu.fpt.laboratory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class LaboratoryManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryManagementServiceApplication.class, args);
    }

}
