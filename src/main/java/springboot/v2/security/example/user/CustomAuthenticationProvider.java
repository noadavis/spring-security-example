package springboot.v2.security.example.user;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import springboot.v2.security.example.entity.User;
import springboot.v2.security.example.repository.UserRepository;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passEncoder;

    public CustomAuthenticationProvider(UserRepository userRepository, CustomPasswordEncoder passEncoder) {
        this.userRepository = userRepository;
        this.passEncoder = passEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByUsername(authentication.getName());
        if (user == null)
            throw new BadCredentialsException("invalid_username_or_password");
        String password = authentication.getCredentials().toString();
        if (!passEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("invalid_username_or_password");
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getRoles());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}