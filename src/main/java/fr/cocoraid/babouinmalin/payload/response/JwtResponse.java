package fr.cocoraid.babouinmalin.payload.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UUID id;
	private String email;
	private String name, surname;

	public JwtResponse(String accessToken, UUID id,  String email, String name, String surname) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
	}

}
