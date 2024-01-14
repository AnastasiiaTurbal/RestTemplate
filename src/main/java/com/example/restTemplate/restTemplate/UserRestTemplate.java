package com.example.restTemplate.restTemplate;

import com.example.restTemplate.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestTemplate {

    private final RestTemplate restTemplate;
    private final String url = "http://94.198.50.185:7081/api/users";

    public UserRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<User[]> getUsers() {
        return restTemplate.getForEntity(url, User[].class);
    }

    public String addNewUser(HttpEntity<?> entity) {
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }

    public String editUser(HttpEntity<?> entity) {
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
    }

    public String deleteUser(HttpEntity<?> entity, Long id) {
        return restTemplate.exchange(url+"/"+id, HttpMethod.DELETE, entity, String.class).getBody();
    }

}



