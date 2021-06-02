package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserMinimalDTO save(User user) {


        return userMapper.userMinimalFromUser(userRepository.save(user));
    }

    public UserMinimalDTO edit(Long id, String username) {
        User actUser = findById(id);
        actUser.setUsername(username);
        return save(actUser);


    }

    public List<UserListDTO> allTeachersObjForList() {
        Role roles = roleRepository.findByName(ERole.TEACHER).get();
        List<User> allByRoles = userRepository.findAllByRoles(roles);

        List<UserListDTO> teacher = allByRoles
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());

        return teacher;
    }

    public List<UserListDTO> allTeachersForList() {
        Role role= roleRepository.findByName(ERole.valueOf("TEACHER")).get();

        List<User> userByRoles = userRepository.findUserByRoles(role);
        List<UserListDTO> collect = userByRoles
                .stream().map(userMapper::userListDtoFromUser)
                .collect(Collectors.toList());

        return collect;
    }

    public void create(UserDTO user) {
        Set<Role> roles = new HashSet<>();
        Role e = roleRepository.findByName(ERole.TEACHER).get();
        roles.add(e);
        User newUser = User.builder().username(user.getName())
                .email(user.getEmail())
                .roles(roles)
                .password(encoder.encode(user.getPassword())).build();
        userRepository.save(newUser);

    }

}
