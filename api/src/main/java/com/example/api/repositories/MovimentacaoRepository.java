package com.example.api.repositories;
import com.example.api.models.MovimentacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Long> {
}