package com.algaworks.algafood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class HelloController {

	@Autowired
	private AtivacaoClienteService ativacaoClienteService;
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		ativacaoClienteService.ativar(new Cliente("João", "joaoxyz@gmail.com", "88999999999"));
		return "Hello!";
	}
}
