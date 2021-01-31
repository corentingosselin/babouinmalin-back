package fr.cocoraid.babouinmalin.model.user;

import lombok.Data;

@Data
public class UserEmailUpdate {

    private String oldEmail;
    private String newEmail;

}
