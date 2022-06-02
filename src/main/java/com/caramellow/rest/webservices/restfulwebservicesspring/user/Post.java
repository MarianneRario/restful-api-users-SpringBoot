package com.caramellow.rest.webservices.restfulwebservicesspring.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id; // primary key
    private String description;
    @ManyToOne(fetch = FetchType.LAZY) // see notes.md
    @JsonIgnore
    private User user; // foreign key


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description +
                '}';
    }
}
