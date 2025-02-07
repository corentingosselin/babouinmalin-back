package fr.cocoraid.babouinmalin.service;

import fr.cocoraid.babouinmalin.exceptions.UserNotFoundException;
import fr.cocoraid.babouinmalin.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService  {
    List<User> listAllUser();
    boolean isSamePassword(String crypted, String password);
    User createUser(User user);

    User saveUser(User user);

    User getUser(UUID id) throws UserNotFoundException;

    User getUser(String email) throws UserNotFoundException;

    boolean isUserExists(String email);

    void deleteUser(UUID id);

    int getUserAmount();

}
