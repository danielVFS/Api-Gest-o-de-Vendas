package com.daniel.gvendas.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.gvendas.entities.Produto;
import com.daniel.gvendas.services.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria/{codigoCategoria}/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@ApiOperation(value = "Lista todos Produtos")
	@GetMapping
	public List<Produto> findAll(@PathVariable Long codigoCategoria) {
		return produtoService.findAllByCategory(codigoCategoria);
	}

	@ApiOperation(value = "Lista um Produto por ID")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Produto>> findById(@PathVariable Long codigoCategoria, @PathVariable Long id) {
		Optional<Produto> produto = produtoService.findById(id, codigoCategoria);

		return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Criar um Produto")
	@PostMapping
	public ResponseEntity<Produto> create(@Valid @RequestBody Produto produto) {
		Produto newProduto = produtoService.create(produto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newProduto);
	}
}
