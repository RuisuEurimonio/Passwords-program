package com.example.demo.controlador;

import com.example.demo.modelo.crud.LoginRepository;
import com.example.demo.modelo.crud.PasswordRepository;
import com.example.demo.vista.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
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

    @Autowired
    private PasswordRepository passwordRepository;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AplicationPasswords.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            Login login = new Login();
            LoginController controller = new LoginController(loginModel, login, passwordRepository);
            login.setVisible(true);
            BackupController bc = new BackupController();
        };
    }

}
