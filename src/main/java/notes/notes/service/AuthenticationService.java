package notes.notes.service;


import lombok.RequiredArgsConstructor;
import notes.notes.adapter.UserAdapter;
import notes.notes.domain.Role;
import notes.notes.dto.JwtAuthenticationResponse;
import notes.notes.dto.SignInRequest;
import notes.notes.dto.UserDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import notes.notes.domain.User;
import notes.notes.dto.SignUpRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signup(SignUpRequest request) throws InvalidArgumentException {
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        UserDTO userDTO = UserAdapter.getUserDTO(user);

        userDTO = userService.createUser(userDTO);
        var jwt = jwtService.generateToken(UserAdapter.getUserEntity(userDTO));

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
