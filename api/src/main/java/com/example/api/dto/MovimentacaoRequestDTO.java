package com.example.api.dto;

import com.example.api.models.TipoMovimento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MovimentacaoRequestDTO {
    @NotNull(message = "ID do produto é obrigatório.")
    private Long produtoId;

    @NotNull(message = "ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "Tipo de movimento (ENTRADA/SAIDA) é obrigatório.")
    private TipoMovimento tipoMovimento;

    @NotNull(message = "Quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;

    // Getters e Setters
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(TipoMovimento tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}