package com.caramellow.rest.webservices.restfulwebservicesspring.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "jpa/user")
public class PostJPAResourceController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/{id}/posts")
    protected List<Post> retrieveAllPosts(@PathVariable int id){
        // first we need to find the user with the specific id
        Optional<User> userOptional =  userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("Id - " + id);
        }

        // then, return post
        return userOptional.get().getPost();
    }

    @GetMapping(path = "{id}/posts/{post_id}")
    protected EntityModel<Post> retrievePost(@PathVariable int id, @PathVariable int post_id) throws Exception {
        // check if the user exist
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("Id - " + id);
        }

        // check if the post exist
        Optional<Post> post = postRepository.findById(post_id);
        if(!post.isPresent()){
            throw new Exception("Post does not exists");
        }

        EntityModel<Post> entityModel = EntityModel.of(post.get()); // this is what I want to return (specific post)
        WebMvcLinkBuilder linkTo =  WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllPosts(id));
        entityModel.add(linkTo.withRel("all-posts")); // this is what I want to add (link to retrieve all users)


        return entityModel;



    }

    @PostMapping(path = "{id}/posts")
    protected ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post){

        // first, we need to retrieve the user
        Optional<User> userOptional = userRepository.findById(id);
        // if the user doesn't exist, throw an exception
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("Id - " + id);
        }
        // create an instance of User
        User user = userOptional.get();
        // map the user to the post
        post.setUser(user);
        // saved the post to the database
        Post savedPost = postRepository.save(post);


        // return a "Created" response
        // current uri should be -> /users/{id} -> /users/savedUser.getId()
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()) // -> kung ano ang ipapalit sa .path("/{id}")
                .toUri();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        // set the URI for the created resource into the response
        return new ResponseEntity<Object>("New Post Successfully Created: "
                + location, responseHeaders, HttpStatus.CREATED);
    }


}
