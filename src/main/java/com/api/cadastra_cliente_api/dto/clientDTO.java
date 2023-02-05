package com.api.cadastra_cliente_api.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record clientDTO(
        @Pattern(regexp = "[A-Za-z].* [A-Za-z].*", message = "Deve ter nome e sobrenome") @Size(min = 4, message = "O nome deve ter no minimo 4 caracteres") @NotBlank(message = "O Campo 'name' é obrigatório") String name,
        @NotBlank(message = "O Campo 'cpf' é obrigatório") String cpf,
        @Past(message = "Deve ser uma data válida e o formato da data deve ser no formato americano 'yyyy-mm-dd'") Date birthDate) {

}
