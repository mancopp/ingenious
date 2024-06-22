package pl.uni.opole.ingenious.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.uni.opole.ingenious.models.Comment;
import pl.uni.opole.ingenious.services.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController extends GenericController<Comment> {

    private final CommentService commentService;

    protected CommentController(CommentService service) {
        super(service);
        this.commentService = service;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.findByUserId(userId));
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<Comment>> getBySongId(@PathVariable Long songId) {
        return ResponseEntity.ok(commentService.findBySongId(songId));
    }

    @GetMapping("/timestamp")
    public ResponseEntity<List<Comment>> getByTimestampBetweenAndSortByUser(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(commentService.findByTimestampBetweenAndSortByUser(start, end));
    }

    @PostMapping("/addCommentToSong")
    public ResponseEntity<Void> addCommentToSong(@RequestBody Comment comment) {
        // Call transactional method
        commentService.addCommentToSong(comment);

        return ResponseEntity.ok().build();
    }
}
