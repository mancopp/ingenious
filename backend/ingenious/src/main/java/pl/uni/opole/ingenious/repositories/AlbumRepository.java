package pl.uni.opole.ingenious.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.Album;

import java.util.List;

@Repository
public interface AlbumRepository extends GenericRepository<Album> {

    List<Album> findByTitle(String title);

    @Query("SELECT a FROM Album a JOIN a.tags t WHERE t.name = :tagName")
    List<Album> findByTagName(@Param("tagName") String tagName);

    List<Album> findByIdGreaterThan(Long id);
}
