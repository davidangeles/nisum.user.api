package com.nisum.user.api.util;

import com.nisum.user.api.model.dto.*;
import com.nisum.user.api.model.entity.Phone;
import com.nisum.user.api.model.entity.User;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public final class Util {

  public static String readFileJson(String jsonFile) throws IOException {
    File file = new File("src/test/resources/json/" + jsonFile);
    return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
  }

  public static String generateId() {
    return UUID.randomUUID().toString();
  }

  public static LocalDateTime getDateTest() {
    return LocalDateTime.of(2022, 05, 14, 15, 33, 16, 901);
  }

  public static UserRequest getUserRequest() {
    PhoneRequest phoneRequest = PhoneRequest
        .builder()
        .number("1234567")
        .citycode("1")
        .contrycode("57")
        .build();

    UserRequest request = UserRequest
        .builder()
        .name("Juan Rodriguez")
        .email("juan4@rodriguez.cl")
        .password("Tunter00")
        .phones(Arrays.asList(phoneRequest))
        .build();

    return request;
  }

  public static UserResponse getUserResponse(String id, LocalDateTime date) {
    UserResponse userResponse = UserResponse
        .builder()
        .id(id)
        .name("Juan Rodriguez")
        .token("eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTI1NjAzOTYsImlzcyI6Imh0dHBzOi8vd3d3Lm5pc3VtLmNvbSIsInN1YiI6Imp1YW40Q" +
            "HJvZHJpZ3Vlei5jbCIsImV4cCI6MTY1MjU2NzU5Nn0.ViOYx0RGUFWYhJH-lg_X2oW61MkELdQwyj6LzQxEMgn6G7N3i-hI8Xrc0H5vW" +
            "PpAqx1USlU6Fu_3zzNtRSKtKQ")
        .isActive(UserStatus.INACTIVE)
        .lastLogin(date)
        .created(date)
        .modified(date)
        .build();

    return userResponse;
  }

  public static User getUserBD() {
    Phone phone = Phone
        .builder()
        .number("1234567")
        .cityCode("1")
        .countryCode("57")
        .build();

    User user = User
        .builder()
        .id(Util.generateId())
        .name("Juan Rodriguez")
        .email("juan4@rodriguez.cl")
        .password("Tunter00")
        .token("eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTI1NjAzOTYsImlzcyI6Imh0dHBzOi8vd3d3Lm5pc3VtLmNvbSIsInN1YiI6Imp1YW40Q" +
            "HJvZHJpZ3Vlei5jbCIsImV4cCI6MTY1MjU2NzU5Nn0.ViOYx0RGUFWYhJH-lg_X2oW61MkELdQwyj6LzQxEMgn6G7N3i-hI8Xrc0H5vW" +
            "PpAqx1USlU6Fu_3zzNtRSKtKQ")
        .isActive(UserStatus.INACTIVE)
        .phoneList(Arrays.asList(phone))
        .build();

    return user;
  }

  public static UserUpdateRequest getUserUpdateRequest(String name, String email, String password) {
    return UserUpdateRequest
        .builder()
        .name(name)
        .email(email)
        .password(password)
        .build();
  }

}
