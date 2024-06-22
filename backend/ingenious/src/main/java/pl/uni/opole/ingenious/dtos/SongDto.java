package pl.uni.opole.ingenious.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SongDto {
    @NotNull(message = "title is required")
    @NotEmpty(message = "title is required")
    private String title;

    @NotNull(message = "artist is required")
    @NotEmpty(message = "artist is required")
    private String artist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
