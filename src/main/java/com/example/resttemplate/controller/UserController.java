package com.example.resttemplate.controller;

import com.example.resttemplate.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/users")
public class UserController {

    private final RestTemplate restTemplate;
    private List<String> cookies;
    private final String url = "http://94.198.50.185:7081/api/users";
    HttpHeaders headers = new HttpHeaders();
    User user = new User(3L, "James", "Brown", 3);

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping()
    public void getUsers() {
        String answer = "";
        ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
        cookies = response.getHeaders().get("Set-Cookie");
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        headers.setContentType(MediaType.APPLICATION_JSON);
        answer += addNewUser();
        answer += editUser();
        answer += deleteUser(3);
        System.out.println(answer);
    }

    @PostMapping()
    public String addNewUser() {
        HttpEntity<?> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    @PutMapping()
    public String editUser() {
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<?> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {
        HttpEntity<?> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url+"/"+id, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }
}



