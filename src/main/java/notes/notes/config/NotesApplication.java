package notes.notes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@SpringBootApplication
@EntityScan("notes.notes.domain")
@EnableJpaRepositories("notes.notes.repository")
@ComponentScan(basePackages = {"notes.notes.controller", "notes.notes.service"})
public class NotesApplication implements CommandLineRunner {

    @Autowired
    public NotesApplication(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotesApplication.class, args);
    }


    private final RequestMappingHandlerMapping handlerMapping;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started successfully . . . .");
        System.out.println("List of all registered endpoints:");

        Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : mappings.keySet()) {
            assert info.getPatternsCondition() != null;
            System.out.println(info);
        }
    }
}
