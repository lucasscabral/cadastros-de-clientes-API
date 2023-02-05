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
        String withdrawPointsFromCpf = req.cpf().replaceAll("\\.", "");
        String cpfWithoutMask = withdrawPointsFromCpf.replaceAll("-", "");

        // valida para ver se o CPF possue 11 dígitos
        if (cpfWithoutMask.length() != 11) {
            return "CPF inválido, o mesmo deve ter 11 dígitos";
        }

        String checkDigits = cpfWithoutMask.substring(9, 11);

        String isTheCpfValid = logicToValidateCpf(cpfWithoutMask, checkDigits);

        if (isTheCpfValid != "validou") {
            return isTheCpfValid;
        }

        List<client> getClient = repository.findByCpf(cpfWithoutMask);
        if (!getClient.isEmpty()) {
            return "Já existe um usuário com esse cpf";
        } else {

            repository.save(new client(req.name(), req.birthDate(), cpfWithoutMask));
            return "ok";
        }
    }

    public String logicToValidateCpf(String cpfWithoutMask, String checkDigits) {

        int sum = 0;
        int descending = 10;

        for (int i = 0; i < 9; i++) {
            // Converte um Character para String, para poder converter essa String em um
            // Inteiro
            char numberCharacter = cpfWithoutMask.charAt(i);

            // Converte a String em um Inteiro
            Integer numberConverted = Integer.parseInt(Character.toString(numberCharacter));

            int multipliesByTheRespective = numberConverted * descending;

            sum = sum + multipliesByTheRespective;
            descending--;
        }

        int rest = (sum * 10) % 11;
        if (rest == 10) {
            rest = 0;
        }

        if (rest != Integer.parseInt(checkDigits.substring(0, 1))) {
            return "CPF inválido";
        }

        int sum2 = 0;
        int descending2 = 11;

        for (int i = 0; i < 10; i++) {
            // Converte um Character para String, para poder converter essa String em um
            // Inteiro
            char numberCharacter = cpfWithoutMask.charAt(i);

            // Converte a String em um Inteiro
            Integer numberConverted = Integer.parseInt(Character.toString(numberCharacter));

            int multipliesByTheRespective = numberConverted * descending2;

            sum2 = sum2 + multipliesByTheRespective;
            descending2--;
        }

        int rest2 = (sum2 * 10) % 11;
        if (rest2 == 10) {
            rest2 = 0;
        }

        if (rest2 != Integer.parseInt(checkDigits.substring(1, 2))) {
            return "CPF inválido";
        }
        return "validou";
    }

    public List<client> getClientByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

}