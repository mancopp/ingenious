package pl.uni.opole.ingenious.repositories;

import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.Song;

@Repository
public interface SongRepository extends GenericRepository<Song> {
}
