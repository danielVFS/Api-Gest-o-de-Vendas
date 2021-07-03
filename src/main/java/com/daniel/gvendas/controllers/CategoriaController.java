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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.gvendas.entities.Categoria;
import com.daniel.gvendas.services.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@ApiOperation(value = "Lista todas categorias")
	@GetMapping
	public List<Categoria> listAll() {
		return categoriaService.listAll();
	}
	
	@ApiOperation(value = "Lista uma categoria por ID")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Categoria>> findById(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		
		return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Cria uma categoria")
	@PostMapping
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria) {
		Categoria newCategoria = categoriaService.create(categoria);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
	}
	
	@ApiOperation(value = "Atualiza uma categoria")
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
		Categoria newCategoria = categoriaService.update(id, categoria);
		
		return ResponseEntity.ok(newCategoria);
	}
}
