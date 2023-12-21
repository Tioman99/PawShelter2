package dk.lucashermann.pawshelter2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dk.lucashermann.pawshelter2.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    // Hi :)
}
