package aliquid.lasttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.*;
import java.util.Arrays;

import javax.swing.text.html.parser.Entity;

@SpringBootApplication
public class Application {

    static RestTemplate restTemplate = new RestTemplate();
    static String url = "http://94.198.50.185:7081/api/users";
    static User user;
    static String cookie;
    static HttpHeaders headers = new HttpHeaders();
    static String answer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        getAllUsers();
    }

    public static void getAllUsers() {

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, requestEntity, String.class);

        String users = responseEntity.getBody();
        System.out.println(users);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        cookie = responseHeaders.toString().substring(13, 74);

        headers.add("Cookie", cookie);


        user = new User(3L, "James", "Brown", (byte) 25);
        requestEntity = new HttpEntity<>(user, headers);
        addUser(requestEntity);

        user.setName("Thomas");
        user.setLastName("Shelby");
        editUser(requestEntity);

        deleteUser(requestEntity);
    }

    public static void addUser(HttpEntity<Object> requestEntity){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String cookie = responseHeaders.toString().substring(13, 74);
        headers.set("Cookie", cookie);

        answer = responseEntity.getBody();
        System.out.println("answer:" + answer);
    }

    public static void editUser(HttpEntity<Object> requestEntity){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, requestEntity, String.class);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String cookie = responseHeaders.toString().substring(13, 74);
        headers.set("Cookie", cookie);

        answer += responseEntity.getBody();
        System.out.println("answer:" + answer);
    }

    public static void deleteUser(HttpEntity<Object> requestEntity){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url+ "/" +user.getId(), HttpMethod.DELETE, requestEntity, String.class);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String cookie = responseHeaders.toString().substring(13, 74);
        headers.set("Cookie", cookie);

        answer += responseEntity.getBody();
        System.out.println("answer:" + answer);
    }

}
