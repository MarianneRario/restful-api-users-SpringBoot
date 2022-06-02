package com.caramellow.rest.webservices.restfulwebservicesspring.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/jpa/user")
public class UserJPAResourceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    protected List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("Id: " + id);
        }

         /* HATEOAS
        - provide link to view all users
        - "all-users", SERVER_PATH + "users" */
        // create a resource that will show all the users when clicked (link)

        EntityModel<User> entityModel = EntityModel.of(user.get()); // this is what I want to return (specific user)
        WebMvcLinkBuilder linkTo =  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); // this is what I want to add (link to retrieve all users)


        return entityModel;
    }


    /* create a new user using the annotation @RequestBody -> mag a'add ka ng request sa body ng api
     *    {
     *     "name": "P",
     *      "birthdate": "2972-05-28T12:25:18.239+00:00"
     *     }
     */
    // @Valid annotation means na dapat validated tong request na to
    @PostMapping
    protected ResponseEntity<Object> createNewUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        // return a "Created" response
        // current uri should be -> /users/{id} -> /users/savedUser.getId()
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()) // -> kung ano ang ipapalit sa .path("/{id}")
                .toUri();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("MyResponseHeader", "MyValue");
        // set the URI for the created resource into the response
        return new ResponseEntity<Object>("Successfully Created: " +
                location, responseHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    protected void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id); // this will return a user result

    }

}
