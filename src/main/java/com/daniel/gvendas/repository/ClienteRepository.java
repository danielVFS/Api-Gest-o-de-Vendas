package com.daniel.gvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.gvendas.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findByNome(String nome);
}
