package com.daniel.gvendas.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.gvendas.dto.cliente.ClienteRequestDTO;
import com.daniel.gvendas.dto.cliente.ClienteResponseDTO;
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
	public List<ClienteResponseDTO> findAll() {
		return clienteService.listAll().stream().map(cliente -> ClienteResponseDTO.convertToClienteDTO(cliente))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Lista um cliente por id")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);

		return cliente.isPresent() ? ResponseEntity.ok(ClienteResponseDTO.convertToClienteDTO(cliente.get()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Cria um cliente")
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> create(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		Cliente newCliente = clienteService.create(clienteDTO.convertToEntity());

		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.convertToClienteDTO(newCliente));
	}
	
	@ApiOperation(value = "Cria um cliente")
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteDTO) {
		Cliente updatedCliente = clienteService.update(id, clienteDTO.convertToEntity(id));

		return ResponseEntity.ok(ClienteResponseDTO.convertToClienteDTO(updatedCliente));
	}
	
	@ApiOperation(value = "Deleta um cliente")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}

}
