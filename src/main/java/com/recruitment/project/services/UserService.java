package com.recruitment.project.services;


import com.recruitment.project.dtos.UserDto;
import com.recruitment.project.exceptions.CredentialsTakenException;
import com.recruitment.project.exceptions.RoleNotFoundException;
import com.recruitment.project.exceptions.UserNotFoundException;
import com.recruitment.project.mappers.UserMapper;
import com.recruitment.project.models.User;
import com.recruitment.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public List<UserDto> allUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) throws RoleNotFoundException,CredentialsTakenException {
        checkCredentials(userDto,userDto.getId());
        User user = userMapper.convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.convertToDto(user);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto,Long id){
        UserDto oldUser = getUser(id);

        checkCredentials(userDto,id);
        User user = userMapper.convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserId(oldUser.getId());
        user = userRepository.save(user);
        return userMapper.convertToDto(user);
    }

    public UserDto getUser(Long id) {
        return userMapper.convertToDto(userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("The user with id="+id+" doesn't exists")
        ));
    }

    public List<UserDto> findUser(String search) {
        List<UserDto> users = new ArrayList<>(allByEmail(search));
        users.addAll(allByUsername(search));
        return users;
    }


    private boolean isEmailAvailable(String email, Long id){
        return userRepository.findTopByEmail(email).isEmpty() ||
                (userRepository.findById(id).isPresent() && userRepository.findById(id).get().getEmail().equals(email));
    }

    private boolean isUsernameAvailable(String username, Long id){
        return userRepository.findTopByUsername(username).isEmpty() ||
                (userRepository.findById(id).isPresent() && userRepository.findById(id).get().getUsername().equals(username));
    }
    private void checkCredentials(UserDto userDto,Long id) throws CredentialsTakenException {
        if(!isEmailAvailable(userDto.getEmail(), id)){
            throw new CredentialsTakenException("This email ("+userDto.getEmail()+") is taken.");
        }
        if(!isUsernameAvailable(userDto.getUsername(), id)){
            throw new CredentialsTakenException("This username ("+userDto.getUsername()+") is taken.");
        }
    }

    private List<UserDto> allByEmail(String email){
        return userRepository.findAllByEmailIgnoreCaseContaining(email)
                .stream().map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
    private List<UserDto> allByUsername(String username){
        return userRepository.findAllByUsernameIgnoreCaseContaining(username)
                .stream().map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }


}
