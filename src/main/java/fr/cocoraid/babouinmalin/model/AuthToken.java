package fr.cocoraid.babouinmalin.model;

import lombok.Data;

@Data
public class AuthToken {

    private String token;
    private String email;

    public AuthToken(String token, String email) {
        this.token = token;
        this.email = email;
    }
}

