package co.com.harcalejo.loanapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LoanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApiApplication.class, args);
	}

}
