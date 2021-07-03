package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.gvendas.entities.Produto;
import com.daniel.gvendas.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> findAllByCategory(Long codigoCategoria) {
		return produtoRepository.findByCategoriaCodigo(codigoCategoria);
	}
	
	public Optional<Produto> findById(Long id, Long codigoCategoria) {
		return produtoRepository.findByCodigo(id, codigoCategoria);
	}
	
	public Produto create(Produto produto) {
		return produtoRepository.save(produto);
	}
}
