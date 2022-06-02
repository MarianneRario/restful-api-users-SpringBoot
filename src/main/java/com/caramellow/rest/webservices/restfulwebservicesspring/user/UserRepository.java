package com.caramellow.rest.webservices.restfulwebservicesspring.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// a Repository is not a class, but an "interface"
@Repository
public interface UserRepository extends JpaRepository <User, Integer>{

}
