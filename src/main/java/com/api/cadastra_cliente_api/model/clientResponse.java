package com.api.cadastra_cliente_api.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import lombok.Data;

@Data
public class clientResponse {
    private final String message;

    public static ResponseEntity<Object> generateResponse(String message, HttpStatusCode status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<Object>(map, status);
    }

}
