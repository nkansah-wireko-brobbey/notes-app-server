package notes.notes.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import notes.notes.adapter.UserAdapter;
import notes.notes.domain.User;
import notes.notes.dto.UserDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public UserDTO createUser(UserDTO userDTO) throws InvalidArgumentException {
        if (userDTO == null)
            throw new InvalidArgumentException("UserDTO is null", HttpStatus.BAD_REQUEST);

        User user = userRepository.save(UserAdapter.getUserEntity(userDTO));

        return UserAdapter.getUserDTO(user);
    }
}
