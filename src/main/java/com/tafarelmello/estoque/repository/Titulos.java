package com.tafarelmello.estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tafarelmello.estoque.model.Titulo;

@Repository
public interface Titulos extends JpaRepository<Titulo, Long> {

	public List<Titulo> findByDescricaoContaining(String descricao);
	
}
