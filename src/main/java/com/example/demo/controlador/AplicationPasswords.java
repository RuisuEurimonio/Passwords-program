package com.example.demo.controlador;

import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.vista.Home;
import com.example.demo.vista.Login;
import com.example.demo.vista.RegisterNewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan("com.example.demo.modelo")
@EnableJdbcRepositories("com.example.demo.modelo")
public class AplicationPasswords {

    @Autowired
    private LoginRepository loginModel;
    
	public static void main(String[] args) {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(AplicationPasswords.class);
            builder.headless(false);
            ConfigurableApplicationContext context = builder.run(args);
	}

        @Bean
        ApplicationRunner applicationRunner(){
            return args -> {
                Home home = new Home();
                Login login = new Login();
                LoginController controller = new LoginController(loginModel, login);
                HomeController hm = new HomeController(home);
                login.setVisible(false);
                home.setVisible(true);
            };
        }
        
}
