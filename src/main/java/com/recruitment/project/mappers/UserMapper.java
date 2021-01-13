package com.recruitment.project.mappers;

import com.recruitment.project.dtos.UserDto;
import com.recruitment.project.exceptions.RoleNotFoundException;
import com.recruitment.project.models.Role;
import com.recruitment.project.models.RolesEnum;
import com.recruitment.project.models.User;
import com.recruitment.project.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class UserMapper {

    private final RoleRepository roleRepository;


    public UserDto convertToDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .roles(null)
                .username(user.getUsername())
                .build();
        List<String> roles = new ArrayList<>();
        for (Role role:
                user.getRoles()) {
            roles.add(role.getRoleName().toString());
        }
        userDto.setRoles(roles);
        return userDto;
    }
    public User convertToEntity(UserDto userDto) throws RoleNotFoundException {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .roles(null)
                .userId(userDto.getId())
                .build();
        List<Role> roles= new ArrayList<>();
        Role role;
        for (String roleName:
                userDto.getRoles()) {
            try {
                role = roleRepository.findTopByRoleName(RolesEnum.valueOf(roleName)).orElse(
                        Role.builder().roleName(RolesEnum.valueOf(roleName)).users(null).build()
                );
                if(role.getRoleId() == 0){
                    roleRepository.save(role);
                }
                roles.add(role);
            }catch (IllegalArgumentException e){
                throw new RoleNotFoundException(roleName + " is not a valid role.");
            }
        }
        user.setRoles(roles);
        return user;
    }
}
