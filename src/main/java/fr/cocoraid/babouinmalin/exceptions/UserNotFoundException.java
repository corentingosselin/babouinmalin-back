package fr.cocoraid.babouinmalin.exceptions;

import java.util.UUID;

public class UserNotFoundException extends Exception {
        public UserNotFoundException(String userId) {
                super(String.format("User is not found with id : '%s'", userId));
        }
}