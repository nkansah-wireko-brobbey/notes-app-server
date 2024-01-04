package notes.notes.adapter;

import notes.notes.domain.User;
import notes.notes.dto.UserDTO;

public class UserAdapter {
    public static User getUserEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return new User();
        }
        return new User(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole()
        );
    }

    public static UserDTO getUserDTO(User user) {
        if (user == null) {
            return new UserDTO();
        }
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }
}
