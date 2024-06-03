package pl.uni.opole.ingenious.services;

import org.springframework.stereotype.Service;
import pl.uni.opole.ingenious.models.Song;
import pl.uni.opole.ingenious.repositories.GenericRepository;

@Service
public class SongService extends GenericService<Song> {
    protected SongService(GenericRepository<Song> repository) {
        super(repository);
    }
}
