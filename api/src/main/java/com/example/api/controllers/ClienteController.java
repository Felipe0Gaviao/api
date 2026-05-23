package com.example.api.controllers;

import com.example.api.dto.ClienteRequestDTO;
import com.example.api.dto.ClienteResponseDTO;
import com.example.api.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok(service.listarTodos());
    }

    @PostMapping
    ResponseEntity<Map<String, Object>> salvar(@Valid  @RequestBody ClienteRequestDTO cliente) {
        service.cadastrar(cliente);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Cliente cadastrado com sucesso."));
    }
}
