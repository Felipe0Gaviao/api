package com.example.api.repositories;
import com.example.api.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    boolean existsByNome(String nome);
}