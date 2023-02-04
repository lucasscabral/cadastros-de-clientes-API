package com.api.cadastra_cliente_api.service;

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

    public String clientRegister(clientDTO req) {
        List<client> getClient = repository.findByCpf(req.cpf());
        if (!getClient.isEmpty()) {
            return "Já existe um usuário com esse cpf";
        } else {
            repository.save(new client(req));
            return "ok";
        }
    }

}
