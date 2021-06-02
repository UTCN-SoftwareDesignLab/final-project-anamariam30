package com.lab4.demo.user;

import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allTeachersObjForList();
    }

    @GetMapping(TEACHER)
    public List<UserListDTO> allTeacher() {
        List<UserListDTO> teachers= userService.allTeachersForList();
        return teachers;
    }

    @DeleteMapping(ENTITY)
    public ResponseEntity<?> delete(@PathVariable @NonNull Long id) {
        try{
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            return  new ResponseEntity<>( HttpStatus.CONFLICT );
        }
    }

    @PatchMapping
    public UserMinimalDTO edit(@RequestBody @NonNull UserMinimalDTO userMinimalDTO) {
        return userService.edit(userMinimalDTO.getId(),userMinimalDTO.getName());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @NonNull UserDTO user) {
        if (authService.existsByUsername(user.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        userService.create(user);
        return ResponseEntity.ok(new MessageResponse("User added successfully!"));
    }

}
