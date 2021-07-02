package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.daniel.gvendas.entities.Categoria;
import com.daniel.gvendas.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> listAll() {
		return categoriaRepository.findAll();
	}
	
	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}
	
	public Categoria create(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Long id, Categoria categoria) {
		Categoria newCategory = categoriaExists(id);
		
		BeanUtils.copyProperties(categoria, newCategory, "codigo");
		
		return categoriaRepository.save(newCategory);
	}

	private Categoria categoriaExists(Long id) {
		Optional<Categoria> categoria = findById(id);
		
		if(categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categoria.get();
	}
}
