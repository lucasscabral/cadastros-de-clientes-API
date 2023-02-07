package com.api.cadastra_cliente_api.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
public class client {

    public client(String name, String cpf, Date birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 160, nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    // @Column(nullable = false)
    @DateTimeFormat(style = "dd/MM/yyyy")
    private Date birthDate;

}
