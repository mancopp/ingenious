package pl.uni.opole.ingenious.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.uni.opole.ingenious.dtos.SongTitleArtistDto;
import pl.uni.opole.ingenious.models.Song;
import pl.uni.opole.ingenious.rest.SearchRequest;
import pl.uni.opole.ingenious.services.SongService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController extends GenericController<Song> {

    private final SongService songService;

    protected SongController(SongService service) {
        super(service);
        this.songService = service;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Song>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(songService.findByTitle(title));
    }

    @GetMapping("/all/min")
    public List<SongTitleArtistDto> getAllMinified(@RequestParam String searchTerm) {
        return songService.getByTextInTitleOrArtist(searchTerm).stream()
                .map(song -> new SongTitleArtistDto(song.getId(), song.getTitle(), song.getArtist()))
                .collect(Collectors.toList());
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<Song>> getByArtist(@PathVariable String artist) {
        return ResponseEntity.ok(songService.findByArtist(artist));
    }

    @GetMapping("/album/{albumTitle}")
    public ResponseEntity<List<Song>> getByAlbumTitle(@PathVariable String albumTitle) {
        return ResponseEntity.ok(songService.findByAlbumTitle(albumTitle));
    }

    @PutMapping("/updateSongAndAlbum/{id}")
    public ResponseEntity<Void> updateSongAndAlbum(@PathVariable Long id, @RequestBody Song updatedSong) {
        Song songToUpdate = songService.getById(id);
        if (songToUpdate == null) {
            return ResponseEntity.notFound().build();
        }

        // Update song details
        songToUpdate.setTitle(updatedSong.getTitle());
        songToUpdate.setArtist(updatedSong.getArtist());

        // Update album details if needed
        if (updatedSong.getAlbum() != null) {
            if (songToUpdate.getAlbum() != null) {
                // Existing album exists, update its details
                songToUpdate.getAlbum().setTitle(updatedSong.getAlbum().getTitle());
                songToUpdate.getAlbum().setTags(updatedSong.getAlbum().getTags());
            } else {
                // Existing album is null, set the new album
                songToUpdate.setAlbum(updatedSong.getAlbum());
            }
        } else {
            // If updatedSong.getAlbum() is null, handle null case
            songToUpdate.setAlbum(null);
        }

        // Call transactional method
        songService.updateSongAndAlbum(songToUpdate);

        return ResponseEntity.ok().build();
    }
}
