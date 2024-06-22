package pl.uni.opole.ingenious.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.uni.opole.ingenious.models.Song;
import pl.uni.opole.ingenious.repositories.AlbumRepository;
import pl.uni.opole.ingenious.repositories.SongRepository;

import java.util.List;

@Service
public class SongService extends GenericService<Song> {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    protected SongService(SongRepository songRepository, AlbumRepository albumRepository) {
        super(songRepository);
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional
    public void updateSongAndAlbum(Song song) {
        songRepository.save(song);
        if (song.getAlbum() != null) {
            albumRepository.save(song.getAlbum());
        }
    }

    public List<Song> findByTitle(String title) {
        return songRepository.findByTitle(title);
    }

    public List<Song> findByArtist(String artist) {
        return songRepository.findByArtist(artist);
    }

    public List<Song> findByAlbumTitle(String albumTitle) {
        return songRepository.findByAlbumTitle(albumTitle);
    }

    public List<Song> getByTextInTitleOrArtist(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAll();
        }
        return songRepository.findByTitleOrArtistContainingIgnoreCase(searchTerm);
    }
}
