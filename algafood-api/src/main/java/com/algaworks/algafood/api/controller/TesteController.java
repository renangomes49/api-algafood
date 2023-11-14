package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam(value = "nome", defaultValue = "Sul-Coreana") String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("cozinhas/exists")
	public boolean cozinhaExists(@RequestParam String nome) {
		return cozinhaRepository.existsByNome(nome);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/cozinha/por-nome")
	public Cozinha cozinhaPorNome(@RequestParam(value = "nome", defaultValue = "Sul-Coreana") String nome) {
		return cozinhaRepository.findByNome(nome).get();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
			@RequestParam BigDecimal taxaFinal) {
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(@RequestParam String nome, @RequestParam Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(@RequestParam String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome(@RequestParam String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(@RequestParam Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
}
