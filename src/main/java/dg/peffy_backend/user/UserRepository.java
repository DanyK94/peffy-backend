package dg.peffy_backend.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public boolean existsByEmail(String email);

    public Optional<User> findByEmail(String email);
    
}
