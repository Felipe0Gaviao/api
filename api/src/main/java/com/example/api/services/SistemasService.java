package com.example.api.services;

import com.example.api.dto.*;
import com.example.api.models.*;
import com.example.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SistemasService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    // Item 4: Lógica de Autenticação / Login simples para SAEP
    public UsuarioModel realizarLogin(LoginRequestDTO dto) {
        UsuarioModel usuario = usuarioRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválidos."));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new IllegalArgumentException("Usuário ou senha inválidos.");
        }
        return usuario;
    }

    // Item 6: Listar Produtos
    public List<ProdutoModel> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Item 6: Cadastrar Produto
    public ProdutoModel cadastrarProduto(ProdutoRequestDTO dto) {
        if (produtoRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Produto com este nome já cadastrado.");
        }
        ProdutoModel produto = new ProdutoModel();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());
        produto.setEstoqueAtual(0); // Começa zerado conforme regra padrão
        return produtoRepository.save(produto);
    }

    // Item 7: Registrar Movimentação e Atualizar Estoque
    @Transactional
    public MovimentacaoModel registrarMovimentacao(MovimentacaoRequestDTO dto) {
        ProdutoModel produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Lógica de atualização de estoque
        if (dto.getTipoMovimento() == TipoMovimento.ENTRADA) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() + dto.getQuantidade());
        } else if (dto.getTipoMovimento() == TipoMovimento.SAIDA) {
            if (produto.getEstoqueAtual() < dto.getQuantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para realizar essa saída.");
            }
            produto.setEstoqueAtual(produto.getEstoqueAtual() - dto.getQuantidade());
        }

        // Salva a alteração do estoque do produto
        produtoRepository.save(produto);

        // Cria o registro histórico da movimentação
        MovimentacaoModel mov = new MovimentacaoModel();
        mov.setProduto(produto);
        mov.setUsuario(usuario);
        mov.setTipoMovimento(dto.getTipoMovimento());
        mov.setQuantidade(dto.getQuantidade());

        return movimentacaoRepository.save(mov);
    }

    // Item 7: Histórico Geral de Movimentações
    public List<MovimentacaoModel> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }

    public UsuarioModel cadastrarUsuario(UsuarioRequestDTO dto) {
        // Verifica se já existe um usuário com o mesmo login
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Este login já está em uso por outro usuário.");
        }

        // Cria e popula a entidade
        UsuarioModel novoUsuario = new UsuarioModel();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setLogin(dto.getLogin());
        novoUsuario.setSenha(dto.getSenha()); // Em ambiente SAEP a senha costuma ser gravada em texto limpo

        // Salva no banco de dados
        return usuarioRepository.save(novoUsuario);
    }

    public void excluirProduto(Long id) {
        // 1. Verifica se o produto realmente existe
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        // 2. Regra de Negócio/SAEP: Impede a exclusão se houver histórico de movimentação
        if (movimentacaoRepository.existsByProdutoId(id)) {
            throw new IllegalArgumentException("Não é possível excluir um produto que possui movimentações no histórico.");
        }

        // 3. Se passou pelas validações, exclui do banco
        produtoRepository.deleteById(id);
    }
}