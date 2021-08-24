package in.timesinternet.punjiup.service.impl;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.repository.UserRepository;
import in.timesinternet.punjiup.security.JWTUtil;
import in.timesinternet.punjiup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtil jwtUtil;



    @Override
    public HashMap<String, Object> login(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        GrantedAuthority role = authentication.getAuthorities().stream().findFirst().get();
        in.timesinternet.punjiup.entity.User user =userRepository.findByEmail(email).get();
        String jwt;
            jwt = jwtUtil.createJWT(email, role.getAuthority());
        String token = "Bearer " + jwt;
        return new HashMap<String, Object>() {{
            put("token", token);
            put("user",user);
        }};
    }
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final Optional<in.timesinternet.punjiup.entity.User>  byEmail= userRepository.findByEmail(s);
        if (byEmail.isPresent()) {
            in.timesinternet.punjiup.entity.User user = byEmail.get();
            List<GrantedAuthority> roles
                    = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority((user.getRole().toString())));
            return new User(user.getEmail(), user.getPassword(), roles);
        } else
            throw new UsernameNotFoundException("user not found");
    }

}
