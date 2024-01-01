package notes.notes.controller;

import notes.notes.dto.UserDTO;
import notes.notes.exception.InvalidArgumentException;
import notes.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws InvalidArgumentException{
        if (userDTO == null)
            throw new InvalidArgumentException("UserDTO is null", HttpStatus.BAD_REQUEST);

        UserDTO newUser = userService.createUser(userDTO);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
