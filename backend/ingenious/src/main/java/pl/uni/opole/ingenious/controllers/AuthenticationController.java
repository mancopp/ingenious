package pl.uni.opole.ingenious.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.uni.opole.ingenious.dtos.LoginUserDto;
import pl.uni.opole.ingenious.dtos.RegisterUserDto;
import pl.uni.opole.ingenious.models.User;
import pl.uni.opole.ingenious.rest.LoginResponse;
import pl.uni.opole.ingenious.services.AuthenticationService;
import pl.uni.opole.ingenious.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
//        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
//        } catch (ConstraintViolationException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
//        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}