package fr.cocoraid.babouinmalin.controller;


import fr.cocoraid.babouinmalin.exceptions.UserNotFoundException;
import fr.cocoraid.babouinmalin.model.user.User;
import fr.cocoraid.babouinmalin.model.user.UserEmailUpdate;
import fr.cocoraid.babouinmalin.model.user.UserPasswordUpdate;
import fr.cocoraid.babouinmalin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    // Get All Notes
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.listAllUser();
    }



    // Get a Single user
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") UUID userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        return user;
    }

    // Update user
    @PutMapping("/users/email/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") UUID userId,
                           @Valid @RequestBody UserEmailUpdate userUpdate) throws UserNotFoundException {

        User user = userService.getUser(userId);
        if(!user.getEmail().equals(userUpdate.getOldEmail())) {
            return new ResponseEntity<>("L'ancienne addresse email ne correspond pas", HttpStatus.BAD_REQUEST);
        }
        // check if new address does not already exists
        if(userService.isUserExists(userUpdate.getNewEmail())) {
            return new ResponseEntity<>("L'addresse email appartient déjà à un autre utilisateur", HttpStatus.BAD_REQUEST);
        }
        user.setEmail(userUpdate.getNewEmail());
        userService.saveUser(user);
        return new ResponseEntity<>("L'addresse mail a été mise à jour !", HttpStatus.OK);
    }

    // Update user
    @PutMapping("/users/password/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable(value = "id") UUID userId,
                                        @Valid @RequestBody UserPasswordUpdate userUpdate) throws UserNotFoundException {

        if(!userUpdate.getPassword().equals(userUpdate.getConfirmPassword())) {
            return new ResponseEntity<>("Les mots de passe ne sont pas identiques", HttpStatus.BAD_REQUEST);
        }

        if(!userUpdate.getPassword().equals(userUpdate.getOldPassword())) {
            return new ResponseEntity<>("Le nouveau mot de passe doit être différent", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(userId);
        if(!encoder.matches(userUpdate.getOldPassword(),user.getPassword())) {
            return new ResponseEntity<>("Votre ancien mot de passe ne correspond pas", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encoder.encode(userUpdate.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    // Delete a Note
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") UUID userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}