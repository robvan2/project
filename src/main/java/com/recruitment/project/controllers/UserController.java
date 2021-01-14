package com.recruitment.project.controllers;

import com.recruitment.project.dtos.AuthenticationResponse;
import com.recruitment.project.dtos.SignInDto;
import com.recruitment.project.dtos.UserDto;
import com.recruitment.project.exceptions.Error;
import com.recruitment.project.exceptions.RoleNotFoundException;
import com.recruitment.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/users")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> allUsers(){
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }
    @PostMapping
    public  ResponseEntity<UserDto> storeUser(@Valid @RequestBody UserDto userDto) throws RoleNotFoundException{
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return new ResponseEntity(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}")
    public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable Long id){
        return new ResponseEntity<>(userService.updateUser(userDto,id), HttpStatus.OK);
    }
    @GetMapping(path="/find")
    public  ResponseEntity<List<UserDto>> findUser(@RequestParam String search){
        return new ResponseEntity<>(userService.findUser(search), HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<Error>> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        List<Error> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new Error(fieldName, errorMessage));
        });
        Map<String, List<Error>> returnValue = new HashMap<>();
        returnValue.put("errors",errors);
        return returnValue;
    }
}
