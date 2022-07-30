package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional 
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}
	
	@Transactional 
	@Override
	public void remover(Restaurante restaurante) {
		restaurante = this.buscar(restaurante.getId());
		entityManager.remove(restaurante);
	}
	
	@Override
	public Restaurante buscar(Long id) {
		return entityManager.find(Restaurante.class, id);
	}
	
	@Override
	public List<Restaurante> listar(){
		return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}
	

}
