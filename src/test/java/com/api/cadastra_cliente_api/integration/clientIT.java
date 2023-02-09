package com.api.cadastra_cliente_api.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.api.cadastra_cliente_api.dto.clientDTO;
import com.api.cadastra_cliente_api.model.client;
import com.api.cadastra_cliente_api.repository.clientRepository;
import com.api.cadastra_cliente_api.service.clientService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class clientIT {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private clientService service;
    @Autowired
    private clientRepository repository;

    /*
     * Antes de executar cada teste, esse método vai deletar todos os dados que tem
     * no banco de dados
     */
    @BeforeEach
    public void up() {
        repository.deleteAll();

    }

    @Test
    @DisplayName("Testa a criação com sucesso do cliente")
    public void createCustomer_ReturnOk() {
        Date date = Date.valueOf("2001-12-14");
        clientDTO clientDto = new clientDTO("Lucas Cabral", "04356036204", date);

        Object response = restTemplate.postForObject("/client", clientDto, Object.class);

        assertEquals(java.util.LinkedHashMap.class, response.getClass());
    }

    @Test
    @DisplayName("Testa a criação de um cliente, mandando um CPF inválido")
    public void createCustomer_ReturnErrorCpfInvalid() {
        Date date = Date.valueOf("2001-12-14");
        clientDTO clientDto = new clientDTO("Lucas Cabral", "04356036214", date);

        Object response = restTemplate.postForObject("/client", clientDto, Object.class);

        assertEquals(java.util.LinkedHashMap.class, response.getClass());
    }

    @Test
    @DisplayName("Testa a criação de um cliente, mandando um CPF com dígitos a mais ou a menos do esperado")
    public void createCustomer_ReturnErrorCpfMoreOrLessDigits() {
        Date date = Date.valueOf("2001-12-14");
        clientDTO clientDto = new clientDTO("Lucas Cabral", "043560362040", date);

        Object response = restTemplate.postForObject("/client", clientDto, Object.class);

        assertEquals(java.util.LinkedHashMap.class, response.getClass());
    }

    @Test
    @DisplayName("Testa a criação de um cliente, mandando um CPF já existente")
    public void createCustomer_ReturnErrorCpfExist() {
        Date date = Date.valueOf("2001-12-14");

        clientDTO client = new clientDTO("Lucas Cabral", "04356036204", date);

        restTemplate.postForEntity("/client", client, String.class);

        service.clientRegister(client);

        String clientEqual = service.clientRegister(client);

        assertEquals("Já existe um usuário com esse cpf", clientEqual);

    }

    @Test
    @DisplayName("Testa o retorno de todos os clientes")
    public void returnAllClients() {
        Date date = Date.valueOf("2001-12-14");
        clientDTO clientDto = new clientDTO("Lucas Cabral", "04356036204", date);

        restTemplate.postForObject("/client", clientDto, String.class);

        List<client> allClientList = repository.findAll();

        assertEquals(1, allClientList.size());
    }

}
