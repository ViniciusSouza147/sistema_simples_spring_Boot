package br.com.springbootgreendogdelivery.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springbootgreendogdelivery.domain.Cliente;
import br.com.springbootgreendogdelivery.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;
	private final String CLIENTE_URL = "cliente/";

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@GetMapping("/")
	public ModelAndView list() {
		Iterable<Cliente> clientes = this.clienteRepository.findAll();
		return new ModelAndView(CLIENTE_URL + "list", "clientes", clientes);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Cliente cliente) {
		return new ModelAndView( CLIENTE_URL + "view", "client", cliente);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute Cliente client) {
		return CLIENTE_URL + "form";
	}
	
	//Metado que chama o formulário de cadastro de novo cliente.
	@PostMapping(params = "form")
	public ModelAndView create(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			return new ModelAndView(CLIENTE_URL + "form", "formErros", result.getAllErrors());
		}

		cliente = this.clienteRepository.save(cliente);
		redirect.addFlashAttribute("globalMessage", "Cliente gravado com sucesso");
		return new ModelAndView("redirect:/" + CLIENTE_URL + "{cliente.id}", "cliente.id", cliente.getId());

	}
	
	//Metado que chama a opção de excluir cliente.
	@GetMapping("remove/{id}")
	public ModelAndView remove(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		this.clienteRepository.deleteById(id);
		Iterable<Cliente> clientes = this.clienteRepository.findAll();
		
		ModelAndView mv = new ModelAndView(CLIENTE_URL + "list", "clientes", clientes);
		mv.addObject("globalMessage", "Cliente removido com sucesso");
		
		return mv;
		
	}
	
	//Metado que chama a tela de alteração de cliente.
	public ModelAndView alterarForm(@PathVariable("id") Cliente cliente) {
		return new ModelAndView(CLIENTE_URL + "form", "cliente", cliente);
	}
}




















