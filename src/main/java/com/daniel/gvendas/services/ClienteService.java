package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.gvendas.entities.Cliente;
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
}
