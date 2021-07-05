package com.daniel.gvendas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.gvendas.entities.Cliente;
import com.daniel.gvendas.services.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@ApiOperation(value = "Lista todas clientes")
	@GetMapping
	public List<Cliente> findAll() {
		return clienteService.listAll();
	}
	
	@ApiOperation(value = "Lista um cliente por id")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);
		
		return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
	}
}
