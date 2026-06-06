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
@CrossOrigin(origins = "*")
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

    // 1. ENDPOINT DE ADIÇÃO (CADASTRO) DE PRODUTO
    @PostMapping("/produtos")
    public ResponseEntity<ProdutoModel> cadastrarProduto(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoModel novoProduto = service.cadastrarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    // ENDPOINT DE EDIÇÃO (ATUALIZAÇÃO) DE PRODUTO
    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProdutoModel> editarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoModel produtoAtualizado = service.editarProduto(id, dto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // 2. ENDPOINT DE EXCLUSÃO DE PRODUTO
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Map<String, Object>> excluirProduto(@PathVariable Long id) {
        service.excluirProduto(id);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Produto excluído com sucesso."
        ));
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