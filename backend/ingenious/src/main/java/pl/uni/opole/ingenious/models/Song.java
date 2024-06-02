package pl.uni.opole.ingenious.models;

import jakarta.persistence.*;

@Entity
@Table(name="songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
