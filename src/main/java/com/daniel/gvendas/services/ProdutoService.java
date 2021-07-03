package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.gvendas.entities.Produto;
import com.daniel.gvendas.exceptions.BusinessRulesException;
import com.daniel.gvendas.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaService categoriaService;

	public List<Produto> findAllByCategory(Long codigoCategoria) {
		return produtoRepository.findByCategoriaCodigo(codigoCategoria);
	}

	public Optional<Produto> findById(Long id, Long codigoCategoria) {
		return produtoRepository.findByCodigo(id, codigoCategoria);
	}

	public Produto create(Produto produto) {
		validateExistingProduto(produto.getCategoria().getCodigo());
		validateDuplicatedProduto(produto);

		return produtoRepository.save(produto);
	}

	private void validateDuplicatedProduto(Produto produto) {
		if (produtoRepository
				.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao())
				.isPresent()) {
			throw new BusinessRulesException(
					String.format("O produto %s já está cadastradado", produto.getDescricao()));
		}
	}

	private void validateExistingProduto(Long codigoCategoria) {
		if (codigoCategoria == null) {
			throw new BusinessRulesException("A categoria não pode ser nula");
		}

		if (categoriaService.findById(codigoCategoria).isEmpty()) {
			throw new BusinessRulesException(
					String.format("A categoria de código %s não existe no cadastro", codigoCategoria));
		}
	}
}
