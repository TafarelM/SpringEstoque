package com.tafarelmello.estoque.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tafarelmello.estoque.model.Produto;
import com.tafarelmello.estoque.model.Status;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProdutoRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void salvar(){
		Produto produto =  new Produto();
	
		produto.setNome("Pão Sovado");
		produto.setDescricao("qualquer coisa");
		produto.setQuantidade(2);
		produto.setValor(new BigDecimal("12"));
		produto.setDataEntrada(new Date());
		produto.setDataVencimento(new Date());
		produto.setStatus(Status.PENDENTE);
		
		Produto produtoRecebido1 = produtoRepository.save(produto);
		Produto produtoRecebido2 = testEntityManager.persist(produto);
		
		Assert.assertNotNull(produtoRecebido1.getId());
		Assert.assertNotNull(produtoRecebido2.getId());
	}
	
	@Test
	public void buscarPorNome(){
		List<Produto> lista = produtoRepository.findByNomeContaining("");
		
		for (Produto produto : lista) {
			System.out.println("Nome: " + produto.getNome());
		}
		
		assertNotNull(lista);
	}
	
	@Test
	public void buscarTodos(){
		Produto produto1 = new Produto();
		
		produto1.setNome("Pão Sovado");
		produto1.setDescricao("qualquer coisa");
		produto1.setQuantidade(2);
		produto1.setValor(new BigDecimal("12"));
		produto1.setDataEntrada(new Date());
		produto1.setDataVencimento(new Date());
		produto1.setStatus(Status.PENDENTE);
		
		Produto produto2 = new Produto();
		
		produto2.setNome("Feijão");
		produto2.setDescricao("aaaaa");
		produto2.setQuantidade(4);
		produto2.setValor(new BigDecimal("22"));
		produto2.setDataEntrada(new Date());
		produto2.setDataVencimento(new Date());
		produto2.setStatus(Status.PENDENTE);
		
		Produto produto3 = new Produto();
		
		produto3.setNome("Maça");
		produto3.setDescricao("ddd");
		produto3.setQuantidade(1);
		produto3.setValor(new BigDecimal("1"));
		produto3.setDataEntrada(new Date());
		produto3.setDataVencimento(new Date());
		produto3.setStatus(Status.PENDENTE);
		
		entityManager.persist(produto1);
		entityManager.persist(produto2);
		testEntityManager.persist(produto3);
		
		List<Produto> lista = produtoRepository.findByNomeContaining("");
		assertThat(lista.get(0).getNome()).isEqualTo(produto1.getNome());
		assertThat(lista.get(1).getNome()).isEqualTo(produto2.getNome());
		assertThat(lista.get(2).getNome()).isEqualTo(produto3.getNome());
	}

}
