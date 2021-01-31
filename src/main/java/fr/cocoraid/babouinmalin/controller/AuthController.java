package fr.cocoraid.babouinmalin.controller;

import fr.cocoraid.babouinmalin.dao.UserRepository;
import fr.cocoraid.babouinmalin.model.user.User;
import fr.cocoraid.babouinmalin.payload.request.LoginRequest;
import fr.cocoraid.babouinmalin.payload.request.SignupRequest;
import fr.cocoraid.babouinmalin.payload.response.JwtResponse;
import fr.cocoraid.babouinmalin.payload.response.MessageResponse;
import fr.cocoraid.babouinmalin.security.jwt.JwtUtils;
import fr.cocoraid.babouinmalin.security.services.UserDetailsImpl;
import fr.cocoraid.babouinmalin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    userDetails.getName(),
                    userDetails.getSurname()));
        } catch(AuthenticationException e) {
            if(e.getMessage().equalsIgnoreCase("Bad credentials"))
                return new ResponseEntity<>("Email ou mot de passe invalide", HttpStatus.UNAUTHORIZED);
            else {
                return new ResponseEntity<>("Le service est temporairement inaccessible", HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> checkToken() {
        return ResponseEntity.ok(new MessageResponse("User is still online"));
    }


    private void register(SignupRequest signUpRequest) {
        // Create new user's account
        User user = new User(
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword());

        userService.createUser(user);
        userRepository.save(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("L'email est déjà utilisé pour ce compte");
        }
        register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/register/users")
    public ResponseEntity<?> registerUsers(@Valid @RequestBody List<SignupRequest> signUpRequest) {
        signUpRequest.stream().filter(r -> !userRepository.existsByEmail(r.getEmail())).forEach(this::register);
        return ResponseEntity.ok(new MessageResponse("Users registered successfully!"));
    }
}