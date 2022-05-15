package com.nisum.user.api.model.dto;

import com.nisum.user.api.model.util.Util;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

  @NotBlank(message = "El campo nombre no tiene que ser vacío")
  private String name;

  @Email(regexp = Util.EMAIL_REGEX, message = "El campo correo tiene formato incorrecto")
  @NotBlank(message = "El correo no puede estar vacío")
  private String email;

  @NotBlank(message = "La campo contraseña no tiene que ser vacío")
  @Pattern(regexp = Util.PASSWORD_REGEX, message = "La contraseña tiene formato incorrecto")
  private String password;

  @NotEmpty(message = "Lista de teléfonos no tiene que ser vacío")
  @Valid
  private List<PhoneRequest> phones;

}
