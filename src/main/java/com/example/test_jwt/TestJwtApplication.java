package com.example.test_jwt;

import com.example.test_jwt.model.Role;
import com.example.test_jwt.model.User;
import com.example.test_jwt.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer"
)
@OpenAPIDefinition(
		info = @Info(title = "Student Attendant Score", version = "v1")
)

public class TestJwtApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TestJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount=userRepository.findByRole(Role.admin);
		if (null==adminAccount){
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Role.admin);
			userRepository.save(user);
		}
	}
}
