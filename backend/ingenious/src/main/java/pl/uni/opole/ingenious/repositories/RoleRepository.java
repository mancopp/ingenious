package pl.uni.opole.ingenious.repositories;

import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends GenericRepository<Role> {
    Optional<Role> findByName(String name);
}