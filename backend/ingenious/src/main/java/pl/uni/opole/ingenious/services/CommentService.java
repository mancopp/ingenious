package pl.uni.opole.ingenious.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.uni.opole.ingenious.models.Comment;
import pl.uni.opole.ingenious.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService extends GenericService<Comment> {

    private final CommentRepository commentRepository;

    protected CommentService(CommentRepository repository) {
        super(repository);
        this.commentRepository = repository;
    }

    @Transactional
    public void addCommentToSong(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public List<Comment> findBySongId(Long songId) {
        return commentRepository.findBySongId(songId);
    }

    public List<Comment> findByTimestampBetweenAndSortByUser(LocalDateTime start, LocalDateTime end) {
        return commentRepository.findByTimestampBetweenAndSortByUser(start, end);
    }
}
