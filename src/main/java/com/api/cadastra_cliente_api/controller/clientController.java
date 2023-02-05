package com.api.cadastra_cliente_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cadastra_cliente_api.dto.clientDTO;
import com.api.cadastra_cliente_api.model.client;
import com.api.cadastra_cliente_api.service.clientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class clientController {

    @Autowired
    private clientService service;

    @GetMapping
    public ResponseEntity<Page<client>> getAllClient(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        return ResponseEntity.ok(service.getAllClient(pageable));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Object> getClientByCpf(@PathVariable String cpf) {
        List<client> clientCpf = service.getClientByCpf(cpf);

        if (clientCpf.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado com esse CPF");
        } else {
            return ResponseEntity.status(200).body(clientCpf);
        }

    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid clientDTO req) {

        String isValid = service.clientRegister(req);
        if (isValid != "ok") {
            return ResponseEntity.status(401).body(isValid);
        } else {
            return ResponseEntity.status(201).body(isValid);
        }

    }

}
