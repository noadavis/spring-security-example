package springboot.v2.security.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.v2.security.example.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
