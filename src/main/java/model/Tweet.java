package model;

import javax.persistence.*;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_author")
    private User author;
    //private publikacje data, autor - pole (publish add) string message
    @Column(name = "publishedAdd")
    private Date publishedAdd;

    @Column(name = "message")
    private String message;

    public Tweet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getPublishedAdd() {
        return publishedAdd;
    }

    public void setPublishedAdd(Date publishedAdd) {
        this.publishedAdd = publishedAdd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}