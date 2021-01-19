package fr.cocoraid.babouinmalin.payload.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UUID id;
	private String username;
	private String email;

	public JwtResponse(String accessToken, UUID id, String username, String email) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
	}

}
