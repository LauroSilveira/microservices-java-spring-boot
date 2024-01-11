package br.com.alurafood.mspayments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPaymentsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MsPaymentsApplication.class, args);
	}

}
