package com.nisum.user.api.service;

import com.nisum.user.api.dao.OperationsDao;
import com.nisum.user.api.model.dto.UserRequest;
import com.nisum.user.api.model.dto.UserResponse;
import com.nisum.user.api.model.dto.UserUpdateRequest;
import com.nisum.user.api.model.entity.User;
import com.nisum.user.api.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

  @MockBean
  private OperationsDao<User> userDao;

  @MockBean
  private TokenService tokenService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  private UserService userService;

  private User userMock;

  private UserResponse userResponseExpected;

  @Before
  public void init() {
    userService = new UserService(tokenService, passwordEncoder, userDao);
    LocalDateTime date = LocalDateTime.now();
    userMock = Util.getUserBD();
    userResponseExpected = Util.getUserResponse(userMock.getId(), date);
  }

  @Test
  public void saveUserTest() {
    UserRequest request = Util.getUserRequest();

    given(userDao.findOne(eq("email"), eq(request.getEmail()))).willReturn(Optional.empty());
    given(passwordEncoder.encode(anyString())).willReturn("Tunter00");
    given(tokenService.generateTokenByUserType(anyString())).willReturn("eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTI1NjAzOTYsImlzcyI6Imh0dHBzOi8vd3d3Lm5pc3VtLmNvbSIsInN1YiI6Imp1YW40Q" +
        "HJvZHJpZ3Vlei5jbCIsImV4cCI6MTY1MjU2NzU5Nn0.ViOYx0RGUFWYhJH-lg_X2oW61MkELdQwyj6LzQxEMgn6G7N3i-hI8Xrc0H5vW" +
        "PpAqx1USlU6Fu_3zzNtRSKtKQ");
    given(userDao.saveOrUpdate(any(User.class))).willReturn(userMock);

    UserResponse userResponse = userService.saveUser(request);
    assertThat(userResponse).usingRecursiveComparison().ignoringFields("created", "modified", "lastLogin").isEqualTo(userResponseExpected);
  }

  @Test
  public void findUserByIdTest() {
    String userId = userResponseExpected.getId();
    given(userDao.findOne(eq("id"), eq(userId))).willReturn(Optional.ofNullable(userMock));

    UserResponse userResponse = userService.findUserById(userId);

    assertThat(userResponse).usingRecursiveComparison().ignoringFields("created", "modified", "lastLogin").isEqualTo(userResponseExpected);
  }

  @Test
  public void updateUserTest() {
    String userId = userResponseExpected.getId();
    UserUpdateRequest request = Util.getUserUpdateRequest("Juan Jorge Rodriguez",
        "juan7@rodriguez.cl", "Xunter22");

    userResponseExpected.setName("Juan Jorge Rodriguez");

    given(userDao.findOne(eq("id"), eq(userId))).willReturn(Optional.ofNullable(userMock));
    given(userDao.saveOrUpdate(any(User.class))).willReturn(userMock);

    UserResponse userResponse = userService.updateUser(userId, request);

    assertThat(userResponse).usingRecursiveComparison().ignoringFields("created", "modified", "lastLogin").isEqualTo(userResponseExpected);
  }

  @Test
  public void deleteUserTest() {
    String userId = userResponseExpected.getId();
    given(userDao.findOne(eq("id"), eq(userId))).willReturn(Optional.ofNullable(userMock));

    userService.deleteUser(userId);

    verify(userDao, times(1)).delete(any(User.class));
  }
}
