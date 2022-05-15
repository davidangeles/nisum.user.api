package com.nisum.user.api.controller;

import com.nisum.user.api.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(TokenService.class)
@WebMvcTest(controllers = TokenController.class)
@AutoConfigureMockMvc
public class TokenControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void serviceTokenTest() throws Exception {
    mockMvc.perform(get("/token").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
