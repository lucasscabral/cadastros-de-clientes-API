package com.api.cadastra_cliente_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

}
