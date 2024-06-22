package pl.uni.opole.ingenious.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.uni.opole.ingenious.models.Album;
import pl.uni.opole.ingenious.models.Tag;
import pl.uni.opole.ingenious.repositories.AlbumRepository;
import pl.uni.opole.ingenious.repositories.TagRepository;

import java.util.List;

@Service
public class AlbumService extends GenericService<Album> {

    private final AlbumRepository albumRepository;
    private final TagRepository tagRepository;

    @Autowired
    protected AlbumService(AlbumRepository repository, TagRepository tagRepository) {
        super(repository);
        this.albumRepository = repository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void addAlbumWithTags(String albumName, List<String> tagNames) {
        Album album = new Album(albumName);
        albumRepository.save(album);

        for (String tagName : tagNames) {
            Tag existingTag = tagRepository.findByName(tagName);

            if (existingTag == null) {
                Tag newTag = new Tag(tagName);
                tagRepository.save(newTag);
                album.addTag(newTag);
            } else {
                album.addTag(existingTag);
            }
        }
    }

    public List<Album> findByTitle(String title) {
        return albumRepository.findByTitle(title);
    }

    public List<Album> findByTagName(String tagName) {
        return albumRepository.findByTagName(tagName);
    }

    public List<Album> findByIdGreaterThan(Long id) {
        return albumRepository.findByIdGreaterThan(id);
    }
}
