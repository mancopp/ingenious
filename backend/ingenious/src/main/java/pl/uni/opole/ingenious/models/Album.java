package pl.uni.opole.ingenious.models;

import jakarta.persistence.*;

@Entity
@Table(name="album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
}
