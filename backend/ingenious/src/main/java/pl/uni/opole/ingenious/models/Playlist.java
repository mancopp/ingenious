package pl.uni.opole.ingenious.models;

import jakarta.persistence.*;
@Entity
@Table(name = "playlists")
@PrimaryKeyJoinColumn(name = "album_id") // Matches the primary key in the 'albums' table
public class Playlist extends Album {

    @Column(name = "description")
    private String description;

    public Playlist() {
        super();
    }

    public Playlist(String title, String description) {
        super(title);
        this.description = description;
    }

    // Getters and setters for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

