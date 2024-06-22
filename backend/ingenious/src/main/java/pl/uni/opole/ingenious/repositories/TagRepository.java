package pl.uni.opole.ingenious.repositories;

import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.Tag;

@Repository
public interface TagRepository extends GenericRepository<Tag> {
    Tag findByName(String name);
}
