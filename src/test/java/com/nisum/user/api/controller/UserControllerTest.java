package com.nisum.user.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.user.api.exception.NotFoundException;
import com.nisum.user.api.model.dto.UserRequest;
import com.nisum.user.api.model.dto.UserResponse;
import com.nisum.user.api.model.dto.UserUpdateRequest;
import com.nisum.user.api.service.TokenService;
import com.nisum.user.api.service.UserService;
import com.nisum.user.api.util.Util;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class, properties = "spring.profiles.active=local")
@Import(TokenService.class)
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private ObjectMapper objectMapper;

  @Before
  public void init() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void serviceSaveUserTest() throws Exception {
    UserRequest request = Util.getUserRequest();
    String jsonResponseExpected = Util.readFileJson("saveUserResponse.json");
    String userId = "5fa78ebf-01a3-4e68-b8ad-eea4630cd660";
    LocalDateTime localDateTime = Util.getDateTest();

    given(userService.saveUser(any(UserRequest.class))).willReturn(Util.getUserResponse(userId, localDateTime));

    mockMvc.perform(post("/api/v1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().json(jsonResponseExpected));
  }

  @Test
  public void findUserByIdTest() throws Exception {
    String userId = "5fa78ebf-01a3-4e68-b8ad-eea4630cd660";
    LocalDateTime localDateTime = Util.getDateTest();
    String jsonResponseExpected = Util.readFileJson("findUserByIdResponse.json");

    given(userService.findUserById(userId)).willReturn(Util.getUserResponse(userId, localDateTime));

    mockMvc.perform(get("/api/v1/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonResponseExpected));
  }

  @Test
  public void updateUserTest() throws Exception {
    String jsonResponseExpected = Util.readFileJson("updateUserResponse.json");
    String userId = "5fa78ebf-01a3-4e68-b8ad-eea4630cd660";
    LocalDateTime localDateTime = Util.getDateTest();
    UserUpdateRequest request = Util.getUserUpdateRequest("Juan Jorge Rodriguez",
        "juan7@rodriguez.cl", "Xunter22");
    UserResponse userResponseExpected = Util.getUserResponse(userId, localDateTime);

    userResponseExpected.setName("Juan Jorge Rodriguez");

    given(userService.updateUser(anyString(), any(UserUpdateRequest.class))).willReturn(userResponseExpected);

    mockMvc.perform(patch("/api/v1/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonResponseExpected));
  }

  @Test
  public void deleteUserByIdTest() throws Exception {
    String userId = "5fa78ebf-01a3-4e68-b8ad-eea4630cd660";

    doThrow(new NotFoundException("Usuario no encontrado")).when(this.userService).deleteUser(userId);

    mockMvc.perform(delete("/api/v1/{id}", userId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.mensaje", Matchers.is("Usuario no encontrado")));
  }


}
