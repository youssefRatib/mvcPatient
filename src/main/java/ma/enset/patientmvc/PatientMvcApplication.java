package ma.enset.patientmvc;

import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import ma.enset.patientmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner cmdlr(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("youssef","1234","1234");
            securityService.saveNewUser("zakaria","1234","1234");
            securityService.saveNewUser("salim","1234","1234");

            securityService.saveNewRole("User","");
            securityService.saveNewRole("Admin","");

            securityService.addRoleToUser("youssef","User");
            securityService.addRoleToUser("youssef","Admin");
            securityService.addRoleToUser("zakaria","User");
            securityService.addRoleToUser("salim","User");
        };
    }

}
