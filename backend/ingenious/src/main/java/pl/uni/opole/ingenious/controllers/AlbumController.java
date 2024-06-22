package pl.uni.opole.ingenious.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.uni.opole.ingenious.models.Album;
import pl.uni.opole.ingenious.services.AlbumService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/albums")
public class AlbumController extends GenericController<Album> {

    private final AlbumService albumService;

    protected AlbumController(AlbumService service) {
        super(service);
        this.albumService = service;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Album>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(albumService.findByTitle(title));
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<Album>> getByTagName(@PathVariable String tagName) {
        return ResponseEntity.ok(albumService.findByTagName(tagName));
    }

    @GetMapping("/idGreaterThan/{id}")
    public ResponseEntity<List<Album>> getByIdGreaterThan(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findByIdGreaterThan(id));
    }

    @PostMapping("/addAlbumWithTags")
    public ResponseEntity<String> addAlbumWithTags(@RequestBody Map<String, Object> request) {
        String albumName = (String) request.get("albumName");
        List<String> tagNames = (List<String>) request.get("tagNames");

        if (albumName == null || tagNames == null || tagNames.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Album> existingAlbum = albumService.findByTitle(albumName);

        if (existingAlbum.isEmpty()) {
            albumService.addAlbumWithTags(albumName, tagNames);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Album with name '" + albumName + "' already exists.");
        }
    }
}
