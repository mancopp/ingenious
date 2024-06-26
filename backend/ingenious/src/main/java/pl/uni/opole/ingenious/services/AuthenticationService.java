package pl.uni.opole.ingenious.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.uni.opole.ingenious.dtos.LoginUserDto;
import pl.uni.opole.ingenious.dtos.RegisterUserDto;
import pl.uni.opole.ingenious.models.Role;
import pl.uni.opole.ingenious.models.User;
import pl.uni.opole.ingenious.repositories.RoleRepository;
import pl.uni.opole.ingenious.repositories.UserRepository;

import java.util.Set;

@Service
public class AuthenticationService {

    private final Validator validator;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            Validator validator,
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {

        Set<ConstraintViolation<RegisterUserDto>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterUserDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(" ");
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        User user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setRole(userRole);

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
