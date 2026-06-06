package com.example.api.controllers;

import com.example.api.dto.*;
import com.example.api.models.*;
import com.example.api.services.SistemasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private SistemasService service;

    // Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequestDTO dto) {
        UsuarioModel usuario = service.realizarLogin(dto);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Login bem-sucedido",
                "usuarioId", usuario.getId(),
                "nome", usuario.getNome()
        ));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Map<String, Object>> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioModel usuarioSalvo = service.cadastrarUsuario(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem", "Usuário cadastrado com sucesso.",
                        "id", usuarioSalvo.getId(),
                        "login", usuarioSalvo.getLogin()
                ));
    }

    // Endpoints de Produtos
    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoModel>> listarProdutos() {
        return ResponseEntity.ok(service.listarProdutos());
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoModel> cadastrarProduto(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarProduto(dto));
    }

    // Endpoints de Movimentações
    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoModel>> listarMovimentacoes() {
        return ResponseEntity.ok(service.listarMovimentacoes());
    }

    @PostMapping("/movimentacoes")
    public ResponseEntity<Map<String, Object>> movimentar(@Valid @RequestBody MovimentacaoRequestDTO dto) {
        service.registrarMovimentacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Movimentação registrada e estoque atualizado com sucesso."));
    }
}