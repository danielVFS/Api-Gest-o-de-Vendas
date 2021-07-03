package com.daniel.gvendas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daniel.gvendas.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoriaCodigo(long codigoCategoria);

	@Query("Select prod" + " from Produto prod" + " where prod.codigo = :codigo"
			+ " and prod.categoria.codigo = :codigoCategoria")
	Optional<Produto> findByCodigo(Long codigo, Long codigoCategoria);
	
	Optional<Produto> findByCategoriaCodigoAndDescricao(long codigoCategoria, String descricao);
}
