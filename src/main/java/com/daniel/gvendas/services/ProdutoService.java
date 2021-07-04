package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	public Produto create(Long codigoCategoria, Produto produto) {
		validateExistingCategoriaProduto(codigoCategoria);
		validateDuplicatedProduto(produto);

		return produtoRepository.save(produto);
	}
	
	public Produto update(Long codigoCategoria, Long codigoProduto, Produto produto) {
		Produto saveProduto = validateExistingProduto(codigoCategoria, codigoProduto);

		validateExistingCategoriaProduto(codigoCategoria);
		validateDuplicatedProduto(produto);

		BeanUtils.copyProperties(produto, saveProduto, "codigo");

		return produtoRepository.save(saveProduto);
	}

	private Produto validateExistingProduto(Long codigoCategoria, Long codigoProduto) {
		Optional<Produto> produto = findById(codigoProduto, codigoCategoria);

		if (produto.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}

		return produto.get();
	}

	private void validateDuplicatedProduto(Produto produto) {
		Optional<Produto> produtoByDescricao = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
		
		if (produtoByDescricao.isPresent() && produtoByDescricao.get().getCodigo() != produto.getCodigo()) {
			throw new BusinessRulesException(
					String.format("O produto %s já está cadastradado", produto.getDescricao()));
		}
	}

	private void validateExistingCategoriaProduto(Long codigoCategoria) {
		if (codigoCategoria == null) {
			throw new BusinessRulesException("A categoria não pode ser nula");
		}

		if (categoriaService.findById(codigoCategoria).isEmpty()) {
			throw new BusinessRulesException(
					String.format("A categoria de código %s não existe no cadastro", codigoCategoria));
		}
	}

}
