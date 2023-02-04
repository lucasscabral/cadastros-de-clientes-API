package com.api.cadastra_cliente_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.cadastra_cliente_api.model.client;

public interface clientRepository extends JpaRepository<client, Long> {
    List<client> findByCpf(String cpf);
}
