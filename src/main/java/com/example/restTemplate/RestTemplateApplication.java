package com.example.restTemplate;

import com.example.restTemplate.config.AppConfig;
import com.example.restTemplate.model.User;
import com.example.restTemplate.restTemplate.UserRestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RestTemplateApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RestTemplateApplication.class, args);
        UserRestTemplate userRestTemplate = context.getBean("userRestTemplate", UserRestTemplate.class);

        String answer = "";

        List<String> cookies = userRestTemplate.getUsers().getHeaders().get("Set-Cookie");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        headers.setContentType(MediaType.APPLICATION_JSON);

        User user = new User(3L, "James", "Brown", 3);
        HttpEntity<?> entity = new HttpEntity<>(user, headers);
        answer += userRestTemplate.addNewUser(entity);

        user.setName("Thomas");
        user.setLastName("Shelby");
        entity = new HttpEntity<>(user, headers);
        answer += userRestTemplate.editUser(entity);

        answer += userRestTemplate.deleteUser(entity, user.getId());

        System.out.println(answer);
    }
}
