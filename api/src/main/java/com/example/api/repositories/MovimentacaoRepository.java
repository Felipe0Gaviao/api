package com.example.api.repositories;

import com.example.api.models.MovimentacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Long> {
    // Método extra para verificar se o produto está amarrado a alguma movimentação
    boolean existsByProdutoId(Long produtoId);
}