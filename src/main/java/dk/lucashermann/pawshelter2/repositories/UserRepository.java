package dk.lucashermann.pawshelter2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dk.lucashermann.pawshelter2.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    Optional<User> deleteUserByEmail(String email);

}
