package fr.cocoraid.babouinmalin.model.token;

import lombok.Data;

@Data
public class LoginUser {
    private String email;
    private String password;
}