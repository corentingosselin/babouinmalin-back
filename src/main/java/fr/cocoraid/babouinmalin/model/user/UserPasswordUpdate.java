package fr.cocoraid.babouinmalin.model.user;

import lombok.Data;

@Data
public class UserPasswordUpdate {

    private String oldPassword;
    private String password;
    private String confirmPassword;

}
