package in.timesinternet.punjiup.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;

public interface UserService extends UserDetailsService {
    HashMap<String,Object> login(String email, String password);

}
