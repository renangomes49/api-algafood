package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties") 
class CadastroCozinhaIT {

	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Cozinha cozinhaNordestina;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	@BeforeEach 
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
		
		databaseCleaner.clearTables();
		prepararDados(); 
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.contentType(ContentType.JSON)
		.when()
			.get()
		.then() 
			.statusCode(HttpStatus.OK.value()); 
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		given()
			.contentType(ContentType.JSON)
		.when()
			.get()
		.then() 
			.body("", Matchers.hasSize(quantidadeCozinhasCadastradas)) 
			.body("nome", Matchers.hasSize(quantidadeCozinhasCadastradas)) 
			.body("nome", Matchers.hasItems("Mineira", "Nordestina")); 
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		 given()
	        .body(jsonCorretoCozinhaChinesa)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post()
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		 given()
	        .pathParam("cozinhaId", cozinhaNordestina.getId())
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cozinhaId}")
	    .then()
	        .statusCode(HttpStatus.OK.value())
	        .body("nome", equalTo(cozinhaNordestina.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
        .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
        .accept(ContentType.JSON)
    .when()
        .get("/{cozinhaId}")
    .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		Cozinha cozinhaMineira = new Cozinha();
		cozinhaMineira.setNome("Mineira");
		cozinhaRepository.save(cozinhaMineira);

		cozinhaNordestina = new Cozinha();
		cozinhaNordestina.setNome("Nordestina");
		cozinhaRepository.save(cozinhaNordestina);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		
	}
}




