package com.api.cadastra_cliente_api.dto;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record clientDTO(
                @Pattern(regexp = "[A-Za-z].* [A-Za-z].*", message = "{firstName_lastName}") @Size(min = 4, message = "{minCharaterName}") @NotBlank(message = "{name.not.blank}") @Schema(description = "Esse é um campo do nome do Cliente", example = "Joãozinho da silva") String name,
                @NotBlank(message = "{cpf.not.blank}") @Schema(description = "Esse é um campo do CPF do Cliente", example = "111.444.777-01") String cpf,
                @Past(message = "{birthDate.format.invalid}") @Schema(description = "Esse é um campo da data de nascimento do Cliente", example = "2000-10-12") Date birthDate) {

}
