//package pl.uni.opole.ingenious.models;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Playlist extends Album {
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User author;
//
//    @Column(name = "description")
//    private String description;
//
//    public Playlist() {
//        super();
//    }
//
//    public Playlist(String title, User author, String description) {
//        super(title);
//        this.author = author;
//        this.description = description;
//    }
//
//    public User getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(User author) {
//        this.author = author;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
