package com.nisum.user.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PhoneRequest {

  @NotBlank(message = "El campo número de telefono no tiene que ser vacío")
  private String number;

  @NotBlank(message = "El código de ciudad no tiene que ser vacío")
  private String citycode;

  @NotBlank(message = "El código de país no tiene que ser vacío")
  private String contrycode;

}
