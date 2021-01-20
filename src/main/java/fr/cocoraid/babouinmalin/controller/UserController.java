package fr.cocoraid.babouinmalin.controller;


import fr.cocoraid.babouinmalin.exceptions.UserNotFoundException;
import fr.cocoraid.babouinmalin.model.User;
import fr.cocoraid.babouinmalin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    // Get All Notes
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.listAllUser();
    }

    // Create a new Note
   /* @PostMapping("/users/register")
    public EntityModel<User> createUser(@Valid @RequestBody User user) throws UserNotFoundException {
        User created = userService.createUser(user);
        EntityModel<User> resource = EntityModel.of(created);
        resource.add(
                linkTo(methodOn(this.getClass()).getUserById(created.getId())).withSelfRel()
        );
        return resource;
    }*/

    // Get a Single user
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") UUID userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        return user;
    }

    // Update user
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") UUID userId,
                           @Valid @RequestBody User userDetails) throws UserNotFoundException {

        User user = userService.getUser(userId);
        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        User updatedUser = userService.saveUser(user);
        return updatedUser;
    }

    // Delete a Note
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") UUID userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}