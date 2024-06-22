package pl.uni.opole.ingenious.repositories;

import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User> {
    Optional<User> findByEmail(String email);
}