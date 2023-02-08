package com.api.cadastra_cliente_api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.cadastra_cliente_api.dto.clientDTO;
import com.api.cadastra_cliente_api.model.client;
import com.api.cadastra_cliente_api.model.clientResponse;
import com.api.cadastra_cliente_api.service.clientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class clientController {

    @Autowired
    private clientService service;

    @GetMapping
    @Operation(summary = "Lista todos os clientes com paginação")
    public ResponseEntity<Page<client>> getAllClient(
            @ParameterObject @PageableDefault(page = 0, size = 5) Pageable pageable) {

        return ResponseEntity.ok(service.getAllClient(pageable));
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Busca um cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o CPF passado")

    })

    public ResponseEntity<Object> getClientByCpf(@PathVariable String cpf) {
        List<client> clientCpf = service.getClientByCpf(cpf);

        if (clientCpf.isEmpty()) {

            return clientResponse.generateResponse("Usuário não encontrado com esse CPF", HttpStatusCode.valueOf(404));
        } else {
            return ResponseEntity.status(200).body(clientCpf);
        }

    }

    @PostMapping
    @Operation(summary = "Cria um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Quando tenta cadastrar um cliente com um CPF já existente no banco de dados"),
            @ApiResponse(responseCode = "422", description = "Quando passa um CPF inválido")

    })
    @ResponseBody
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid clientDTO req) {

        String isValid = service.clientRegister(req);

        if (isValid.equals("Já existe um usuário com esse cpf")) {
            return clientResponse.generateResponse(isValid, HttpStatusCode.valueOf(401));
        }
        if (isValid != "ok") {
            return clientResponse.generateResponse(isValid, HttpStatusCode.valueOf(422));
        } else {
            return clientResponse.generateResponse(isValid, HttpStatusCode.valueOf(201));
        }

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, String> MessageExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldError = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldError, errorMessage);
        });

        return errors;
    }

}
