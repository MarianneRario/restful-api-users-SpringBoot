package com.caramellow.rest.webservices.restfulwebservicesspring.user;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity // makes the User class an entity (javax.persistence)
@Table(name = "users")
public class User{
    @Id // makes the ID
    @GeneratedValue // makes the database generate a value
    private Integer id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;
    @Past
    private LocalDate birthdate;

    @OneToMany(mappedBy = "user") // mappedBy - the field that owns the relationship; creates a relationship column in post table only
    private List<Post> post;

    public User() {
    }

    protected User(String name, LocalDate birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
