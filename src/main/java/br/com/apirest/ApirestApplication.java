package br.com.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.apirest"})
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.apirest.repository"})
@EnableWebMvc
@EnableAutoConfiguration
@RestController
@EnableTransactionManagement
public class ApirestApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ApirestApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	
	//mapeamento Global que reflete em todo o sistema
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*");
		
		registry.addMapping("/utils/**")
		.allowedMethods("*")
		.allowedOrigins("*");
	
	}

}
