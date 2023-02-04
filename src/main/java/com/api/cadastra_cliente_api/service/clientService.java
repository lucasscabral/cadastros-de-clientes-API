package com.api.cadastra_cliente_api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.cadastra_cliente_api.dto.clientDTO;
import com.api.cadastra_cliente_api.model.client;
import com.api.cadastra_cliente_api.repository.clientRepository;

@Service
public class clientService {
    @Autowired
    private clientRepository repository;

    public Page<client> getAllClient(Pageable page) {
        return repository.findAll(page);
    }

}
