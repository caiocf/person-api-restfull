package br.com.mkcf.personapi;

import br.com.mkcf.personapi.config.FileStorageConfig;
import br.com.mkcf.personapi.repository.PersonRepository;
import br.com.mkcf.personapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationProperties
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class PersonApiApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PersonApiApplication.class);

    @Autowired
    private PersonRepository personRepository;

    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(PersonApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //logger.info("Listing books");

        //personRepository.findAll().forEach(System.out::println);
    }
}
