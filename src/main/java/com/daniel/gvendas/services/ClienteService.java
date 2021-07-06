package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.gvendas.entities.Cliente;
import com.daniel.gvendas.exceptions.BusinessRulesException;
import com.daniel.gvendas.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> listAll() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public Cliente create(Cliente cliente) {
		validateDuplicatedCliente(cliente);
		return clienteRepository.save(cliente);
	}
	
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

	private void validateDuplicatedCliente(Cliente cliente) {
		Cliente clienteByNome = clienteRepository.findByNome(cliente.getNome());

		if (clienteByNome != null && clienteByNome.getCodigo() != cliente.getCodigo()) {
			throw new BusinessRulesException(String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
		}		
	}
}
