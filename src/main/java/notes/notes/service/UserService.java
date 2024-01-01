package notes.notes.service;

import jakarta.transaction.Transactional;
import notes.notes.adapter.UserAdapter;
import notes.notes.domain.User;
import notes.notes.dto.UserDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) throws InvalidArgumentException {
        if (userDTO == null)
            throw new InvalidArgumentException("UserDTO is null", HttpStatus.BAD_REQUEST);

        User user = userRepository.save(UserAdapter.getUserEntity(userDTO));

        return UserAdapter.getUserDTO(user);
    }
}
