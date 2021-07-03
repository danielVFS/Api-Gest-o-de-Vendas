package com.daniel.gvendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.daniel.gvendas.entities.Categoria;
import com.daniel.gvendas.exceptions.BusinessRulesException;
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
		validateDuplicatedCategory(categoria);
		return categoriaRepository.save(categoria);
	}

	public void delete(Long id) {
		categoriaRepository.deleteById(id);
	}

	public Categoria update(Long id, Categoria categoria) {
		Categoria newCategory = categoriaExists(id);
		validateDuplicatedCategory(newCategory);

		BeanUtils.copyProperties(categoria, newCategory, "codigo");

		return categoriaRepository.save(newCategory);
	}

	private Categoria categoriaExists(Long id) {
		Optional<Categoria> categoria = findById(id);

		if (categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}

		return categoria.get();
	}

	private void validateDuplicatedCategory(Categoria categoria) {
		Categoria categoriaExists = categoriaRepository.findByNome(categoria.getNome());

		// Quando não estou querendo atualizar alguma categoria
		if (categoriaExists != null && categoriaExists.getCodigo() != categoria.getCodigo()) {
			throw new BusinessRulesException(
					String.format("A categoria %s já está cadastrada", categoria.getNome().toUpperCase()));
		}
	}
}
