package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	@Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.findById(estadoId)
        		.orElseThrow(() -> new EntidadeNaoEncontradaException(
        				String.format("Não existe cadastro de estado com código %d", estadoId)));
            
        cidade.setEstado(estado);
        
        return cidadeRepository.save(cidade);
    }
    
    public void excluir(Long cidadeId) {
    	if(!cidadeRepository.existsById(cidadeId)) {
    		throw new EntidadeNaoEncontradaException(
    				String.format("Não existe um cadastro de cidade com código %d", cidadeId));
        }
            
        cidadeRepository.deleteById(cidadeId);
    }
}
