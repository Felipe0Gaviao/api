package com.example.api.services;

import com.example.api.dto.ClienteRequestDTO;
import com.example.api.dto.ClienteResponseDTO;
import com.example.api.models.ClienteModel;
import com.example.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(c -> new ClienteResponseDTO(c.getNome(), c.getEmail()))
                .toList();
    }

    public ClienteModel cadastrar(ClienteRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Cliente já cadastrado");
        }
        ClienteModel novoCliente = new ClienteModel();
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setTelefone(dto.getTelefone());
        return repository.save(novoCliente);
    }
}
