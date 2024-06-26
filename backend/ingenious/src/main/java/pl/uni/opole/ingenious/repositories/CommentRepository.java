package pl.uni.opole.ingenious.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.uni.opole.ingenious.models.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends GenericRepository<Comment> {

    List<Comment> findByUserId(Long userId);

    List<Comment> findBySongId(Long songId);


    @Query(value = "SELECT * FROM comments WHERE user_id = :userId", nativeQuery = true)
    List<Comment> findByUserIdNative(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c WHERE c.timestamp BETWEEN :start AND :end ORDER BY c.user.fullName")
    List<Comment> findByTimestampBetweenAndSortByUser(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
