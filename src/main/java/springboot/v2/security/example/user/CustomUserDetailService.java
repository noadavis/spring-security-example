package springboot.v2.security.example.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.v2.security.example.entity.User;
import springboot.v2.security.example.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    public CustomUserDetailService(UserRepository userRepository, CustomPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setActive(true);
        user.setFullname(userForm.getFullname());
        user.setEmail(userForm.getEmail());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
    }

    public String addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) return String.format("%s exist", user.getUsername());
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userRepository.save(user);
            if (newUser.getId() > 0) return String.format("%s created", user.getUsername());
        } catch (Exception e) {
            return String.format("%s not created", user.getUsername());
        }
        return String.format("%s created", user.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("invalid_username_or_password");
        return user;
    }
}
