package com.tafarelmello.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tafarelmello.estoque.model.Produto;
import com.tafarelmello.estoque.model.Status;
import com.tafarelmello.estoque.repository.ProdutoRepository;
import com.tafarelmello.estoque.repository.filter.ProdutoFilter;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public void salvar(Produto produto) {

		produtoRepository.save(produto);
	}

	public void excluir(Long id) {
		produtoRepository.delete(id);
	}

	public String receber(Long id) {
		Produto produto = produtoRepository.findOne(id);
		produto.setStatus(Status.RECEBIDO);
		produtoRepository.save(produto);

		return Status.RECEBIDO.getDescricao();
	}

	public List<Produto> filtrar(ProdutoFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return produtoRepository.findByNomeContaining(nome);
	}

}
