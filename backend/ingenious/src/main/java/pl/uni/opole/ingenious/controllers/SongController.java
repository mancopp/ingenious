package pl.uni.opole.ingenious.controllers;

import org.springframework.web.bind.annotation.*;
import pl.uni.opole.ingenious.models.Song;
import pl.uni.opole.ingenious.services.GenericService;

@RestController
@RequestMapping("/songs")
public class SongController extends GenericController<Song> {

    protected SongController(GenericService<Song> service) {
        super(service);
    }
}
