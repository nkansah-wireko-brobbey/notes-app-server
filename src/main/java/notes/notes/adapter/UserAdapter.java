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
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getName(),
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
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
