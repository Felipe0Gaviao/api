package com.example.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoRequestDTO {
    @NotBlank(message = "O nome do produto é obrigatório.")
    private String nome;

    private String Black; // Descrição opcional
    private String descricao;

    @NotNull(message = "Estoque mínimo é obrigatório.")
    @Min(value = 0, message = "O estoque mínimo não pode ser negativo.")
    private Integer estoqueMinimo;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
}