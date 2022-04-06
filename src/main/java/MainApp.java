

import model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MainApp {

    static final String URL = "http://94.198.50.185:7081/api/users";
    static String result = "";
    static RestTemplate restTemplate = new RestTemplate();
    static HttpHeaders headers = new HttpHeaders();


    public static void main(String[] args) {

        String cookie = getUsers();
        result = saveUser(cookie)
                + editUser(cookie)
                + deleteUser(cookie);

        System.out.println(result);

    }
    

    public static String getUsers(){
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        String cookie = responseEntity.getHeaders().getFirst("set-cookie");
        return cookie;
    }

    public static String saveUser(String cookie){
        User user = new User(3L, "James", "Brown", (byte) 25);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();
    }

    public static String editUser(String cookie) {
        User editUser = new User(3L, "Thomas", "Shelby", (byte) 27);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> entity = new HttpEntity<>(editUser, headers);
        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
    }

    public static String deleteUser(String cookie) {
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class).getBody();
    }
}
