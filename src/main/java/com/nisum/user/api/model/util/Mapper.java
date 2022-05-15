package com.nisum.user.api.model.util;

import com.nisum.user.api.model.dto.PhoneRequest;
import com.nisum.user.api.model.dto.UserRequest;
import com.nisum.user.api.model.dto.UserResponse;
import com.nisum.user.api.model.dto.UserStatus;
import com.nisum.user.api.model.entity.Phone;
import com.nisum.user.api.model.entity.User;
import com.nisum.user.api.service.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

  private Mapper() {
    throw new UnsupportedOperationException();
  }

  public static User buildUserEntity(UserRequest request, String idGenerated, String token, String password) {
    List<Phone> phoneList = new ArrayList<>();
    User user = User
        .builder()
        .id(idGenerated)
        .email(request.getEmail())
        .password(password)
        .isActive(UserStatus.INACTIVE)
        .token(token)
        .phoneList(phoneList)
        .build();

    request.getPhones().stream().forEach(phoneRequest -> phoneList.add(buildPhoneEntity(user, phoneRequest)));

    return user;
  }

  public static Phone buildPhoneEntity(User user, PhoneRequest phoneRequest) {
    return Phone
        .builder()
        .user(user)
        .number(phoneRequest.getNumber())
        .cityCode(phoneRequest.getCitycode())
        .countryCode(phoneRequest.getContrycode())
        .build();
  }

  public static UserResponse buildGetUserResponse(User entity) {
    return UserResponse
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .created(entity.getCreatedDate())
        .modified(entity.getUpdatedDate())
        .lastLogin(entity.getLastLogin())
        .token(entity.getToken())
        .isActive(entity.getIsActive())
        .build();
  }

  public static UserResponse buildUpdatedUserResponse(User entity) {
    return UserResponse
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .created(entity.getCreatedDate())
        .modified(entity.getUpdatedDate())
        .lastLogin(entity.getLastLogin())
        .token(entity.getToken())
        .isActive(entity.getIsActive())
        .build();
  }

}
