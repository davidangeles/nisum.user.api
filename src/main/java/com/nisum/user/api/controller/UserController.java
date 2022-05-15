package com.nisum.user.api.controller;

import com.nisum.user.api.model.dto.UserRequest;
import com.nisum.user.api.model.dto.UserResponse;
import com.nisum.user.api.model.dto.UserUpdateRequest;
import com.nisum.user.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse saveUser(@RequestBody @Valid UserRequest request) {
    return userService.saveUser(request);
  }

  @GetMapping("/{id}")
  public UserResponse findUserById(@PathVariable("id") String id) {
    return userService.findUserById(id);
  }

  @PatchMapping("/{id}")
  public UserResponse updateUser(@PathVariable("id") String id, @Valid @RequestBody UserUpdateRequest request) {
    return userService.updateUser(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUserById(@PathVariable("id") String id) {
    userService.deleteUser(id);
  }



}
