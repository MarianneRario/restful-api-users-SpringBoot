package com.caramellow.rest.webservices.restfulwebservicesspring.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
