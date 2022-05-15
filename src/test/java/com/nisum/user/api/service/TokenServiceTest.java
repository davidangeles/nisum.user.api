package com.nisum.user.api.service;

import com.nisum.user.api.properties.TokenJwtProperties;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application.properties"})
@Import(TokenService.class)
public class TokenServiceTest {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private TokenJwtProperties tokenJwtProperties;

  @Test
  public void generateTokenByUserTypeTest() {
    String subjectExpected = "test@test.cl";
    String tokenResponse = tokenService.generateTokenByUserType("test@test.cl");
    TokenJwtProperties.TokenProperties tokenProperties = tokenJwtProperties.getTypes().get(TokenJwtProperties.TokenType.USER);
    String subjectResponse = generateTokenSubject(tokenResponse, tokenProperties);

    assertThat(subjectResponse).isEqualTo(subjectExpected);
  }

  @Test
  public void generateTokenBySecurityTypeTest() {
    String subjectExpected = "test@test.cl";
    String tokenResponse = tokenService.generateTokenBySecurityType("test@test.cl");
    TokenJwtProperties.TokenProperties tokenProperties = tokenJwtProperties.getTypes().get(TokenJwtProperties.TokenType.SECURITY);
    String subjectResponse = generateTokenSubject(tokenResponse, tokenProperties);

    assertThat(subjectResponse).isEqualTo(subjectExpected);
  }

  @Test
  public void isTokenValidTest() {
    String tokenValid = tokenService.generateTokenBySecurityType("test@test.cl");
    boolean isTokenValid = tokenService.isTokenValid(TokenJwtProperties.TokenType.SECURITY, tokenValid);

    assertThat(isTokenValid).isEqualTo(true);

    String tokenInValid = "eyJhbGciOiJIUzUxMiJ9";
    boolean isTokenInValid = tokenService.isTokenValid(TokenJwtProperties.TokenType.SECURITY, tokenInValid);

    assertThat(isTokenInValid).isEqualTo(false);
  }

  private String generateTokenSubject(String tokenResponse, TokenJwtProperties.TokenProperties tokenProperties) {
    return Jwts
        .parser()
        .setSigningKey(tokenProperties.getSecret())
        .parseClaimsJws(tokenResponse)
        .getBody().getSubject();
  }


}
