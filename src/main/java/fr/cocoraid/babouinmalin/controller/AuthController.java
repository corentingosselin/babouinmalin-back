package fr.cocoraid.babouinmalin.controller;

import fr.cocoraid.babouinmalin.dao.UserRepository;
import fr.cocoraid.babouinmalin.model.User;
import fr.cocoraid.babouinmalin.payload.request.LoginRequest;
import fr.cocoraid.babouinmalin.payload.request.SignupRequest;
import fr.cocoraid.babouinmalin.payload.response.JwtResponse;
import fr.cocoraid.babouinmalin.payload.response.MessageResponse;
import fr.cocoraid.babouinmalin.security.jwt.JwtUtils;
import fr.cocoraid.babouinmalin.security.services.UserDetailsImpl;
import fr.cocoraid.babouinmalin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword());

        userService.createUser(user);


      /*  User created = userService.createUser(user);
        EntityModel<User> resource = EntityModel.of(created);
        resource.add(
                linkTo(methodOn(this.getClass()).getUserById(created.getId())).withSelfRel()
        );
        return resource;*/

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}