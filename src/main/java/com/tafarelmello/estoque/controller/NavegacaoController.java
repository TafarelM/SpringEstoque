package com.tafarelmello.estoque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavegacaoController {

	@RequestMapping("/home")
	public String novo() {

		return "home";
	}
	
}
