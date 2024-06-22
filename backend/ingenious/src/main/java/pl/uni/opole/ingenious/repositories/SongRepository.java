package pl.uni.opole.ingenious.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.Song;

import java.util.List;

@Repository
public interface SongRepository extends GenericRepository<Song> {

    List<Song> findByTitle(String title);

    List<Song> findByArtist(String artist);

    @Query("SELECT s FROM Song s WHERE s.album.title = :albumTitle")
    List<Song> findByAlbumTitle(@Param("albumTitle") String albumTitle);

    @Query("SELECT s FROM Song s WHERE lower(s.title) LIKE lower(concat('%', :searchTerm, '%')) OR lower(s.artist) LIKE lower(concat('%', :searchTerm, '%'))")
    List<Song> findByTitleOrArtistContainingIgnoreCase(@Param("searchTerm") String searchTerm);
}
