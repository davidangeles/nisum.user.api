package com.nisum.user.api.service;

import com.nisum.user.api.dao.OperationsDao;
import com.nisum.user.api.exception.NotFoundException;
import com.nisum.user.api.exception.UnprocessableEntity;
import com.nisum.user.api.model.dto.UserRequest;
import com.nisum.user.api.model.dto.UserResponse;
import com.nisum.user.api.model.dto.UserUpdateRequest;
import com.nisum.user.api.model.entity.User;
import com.nisum.user.api.model.util.Mapper;
import com.nisum.user.api.model.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final TokenService tokenService;

  private final PasswordEncoder passwordEncoder;

  private final OperationsDao<User> userOperationsDao;

  public UserResponse saveUser(UserRequest request) {
    validateUserByEmail(request.getEmail());

    String idGenerated = Util.generateId();
    String token = tokenService.generateTokenByUserType(request.getEmail());
    String password = passwordEncoder.encode(request.getPassword());
    User user = Mapper.buildUserEntity(request, idGenerated, token, password);

    log.info("Save user: {}", user);

    return Mapper.buildGetUserResponse(userOperationsDao.saveOrUpdate(user));
  }

  @Transactional(readOnly = true)
  public UserResponse findUserById(String id) {
    return Mapper.buildGetUserResponse(validateExistsUserById(id));
  }

  public UserResponse updateUser(String id, UserUpdateRequest request) {
    User user = validateExistsUserById(id);

    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());

    log.info("Updating user. ID: {}, Request: {}", id, request);

    return Mapper.buildUpdatedUserResponse(userOperationsDao.saveOrUpdate(user));
  }

  public void deleteUser(String id) {
    User user = validateExistsUserById(id);

    log.info("Delete user. ID: {}", id);
    userOperationsDao.delete(user);
  }

  private User validateExistsUserById(String id) {
    return userOperationsDao.findOne("id", id)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
  }

  private void validateUserByEmail(String email) {
    userOperationsDao.findOne("email", email).ifPresent(user -> {
      throw new UnprocessableEntity("El correo ya registrado");
    });
  }


}
