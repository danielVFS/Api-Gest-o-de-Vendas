package com.daniel.gvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.gvendas.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Categoria findByNome(String name);
}
