package com.example.api.repositories;
import com.example.api.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByLogin(String login);
}