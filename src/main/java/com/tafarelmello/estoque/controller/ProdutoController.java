package com.tafarelmello.estoque.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tafarelmello.estoque.model.Produto;
import com.tafarelmello.estoque.model.Status;
import com.tafarelmello.estoque.repository.filter.ProdutoFilter;
import com.tafarelmello.estoque.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	private static final String PRODUTO_ADD_VIEW = "produto";

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(PRODUTO_ADD_VIEW);
		mv.addObject(new Produto());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Produto produto, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return PRODUTO_ADD_VIEW;
		}

		try {
			produtoService.salvar(produto);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
			return "redirect:/produto/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return PRODUTO_ADD_VIEW;
		}
	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") ProdutoFilter filtro) {
		List<Produto> listaProduto = produtoService.filtrar(filtro);

		ModelAndView mv = new ModelAndView("PesquisaProduto");
		mv.addObject("produtos", listaProduto);
		return mv;
	}

	@RequestMapping(value = "{id}")
	public ModelAndView edicao(@PathVariable("id") Produto produto) {
		ModelAndView mv = new ModelAndView(PRODUTO_ADD_VIEW);
		mv.addObject(produto);
		return mv;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		produtoService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Excluído com sucesso!");
		return "redirect:/produto";
	}

	@RequestMapping(value = "/{id}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long id) {
		return produtoService.receber(id);
	}

	@ModelAttribute("todosStatusProduto")
	public List<Status> todosStatusProduto() {
		return Arrays.asList(Status.values());
	}

}
