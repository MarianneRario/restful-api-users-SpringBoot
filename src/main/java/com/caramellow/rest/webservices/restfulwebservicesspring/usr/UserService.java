package com.caramellow.rest.webservices.restfulwebservicesspring.usr;

import com.caramellow.rest.webservices.restfulwebservicesspring.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class UserService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 2;

//    static {
//        users.add(new User("Marianne",
//                LocalDate.of(1999, Month.OCTOBER, 7) ));
//        users.add(new User("Paulo",
//                LocalDate.of(1999, Month.JULY, 2)));
//    }

    /* Create three methods
    1. findAll()
    2. save()
    3. findOne()
    4. deleteById() */

    protected List<User> findAll(){
        return users;
    }

    protected User findOne(int id){
        for(User user: users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    protected User saveUser(User user){
        // check if user has an id
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    protected User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user; // return the deleted resource
            }
        }
        return null;
    }


}
