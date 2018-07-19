package br.com.springbootgreendogdelivery.carga;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.springbootgreendogdelivery.domain.Cliente;
import br.com.springbootgreendogdelivery.domain.Item;
import br.com.springbootgreendogdelivery.domain.Pedido;
import br.com.springbootgreendogdelivery.repository.ClienteRepository;

@Component
public class RepositoryTest 
implements ApplicationRunner {

	private static final long ID_CLIENTE_FERNANDO = 11l;
	private static final long ID_CLIENTE_VINICIUS = 22l;

	private static final long ID_ITEM1 = 100l;
	private static final long ID_ITEM2 = 101l;
	private static final long ID_ITEM3 = 102l;

	private static final long ID_PEDIDO1 = 1000l;
	private static final long ID_PEDIDO2 = 1001l;
	private static final long ID_PEDIDO3 = 1002l;

	// Depois, declaramos o repositório de cliente e o método run, que o Spring Boot
	// chamará para ser executado:
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {

		// Começamos declarando os clientes:
		System.out.println(">>>> Iniciando carga de dados... ");
		Cliente fernando = new Cliente(ID_CLIENTE_FERNANDO, "Fernando Loxa", "Fabri");
		Cliente vinicius = new Cliente(ID_CLIENTE_VINICIUS, "Vinicius Souza", "BH");

		// Depois os três itens disponíveis para venda:
		Item dog1 = new Item(ID_ITEM1, "Green Dog tradicional", 25d);
		Item dog2 = new Item(ID_ITEM2, "Green Dog tradicional picante", 27d);
		Item dog3 = new Item(ID_ITEM3, "Green Dog max salada", 30d);

		// Em seguida, a lista de pedidos:
		List<Item> listaPedidoFernando1 = new ArrayList<Item>();
		listaPedidoFernando1.add(dog1);

		List<Item> listaPedidoVinicius1 = new ArrayList<Item>();
		listaPedidoVinicius1.add(dog2);
		listaPedidoVinicius1.add(dog3);

		// Depois, montamos as listas nos objetos de pedido:
		Pedido pedidoFernando = new Pedido(ID_PEDIDO1, fernando, listaPedidoFernando1, dog1.getPreco());
		fernando.novoPedido(pedidoFernando);

		Pedido pedidoVinicius = new Pedido(ID_PEDIDO2, vinicius, listaPedidoVinicius1,
				dog2.getPreco() + dog3.getPreco());
		vinicius.novoPedido(pedidoVinicius);

		System.out.println(">>> Pedido 1 - Fernando: " + pedidoFernando);
		System.out.println(">>> Pedido 2 - Vinicius: " + pedidoVinicius);
	
			// E finalmente, persistimos no banco de dados:
			clienteRepository.saveAndFlush(vinicius);
			System.out.println(">>> Gravando cliente 2: " + vinicius);

			List<Item> listaPedidoFernando2 = new ArrayList<>();
			listaPedidoFernando2.add(dog2);
			
		Pedido pedido2Fernando = new Pedido(ID_PEDIDO3, fernando, listaPedidoFernando2, dog2.getPreco());
		fernando.novoPedido(pedido2Fernando);
		clienteRepository.saveAndFlush(fernando);

		System.out.println(">>> Pedido 2 - Fernando: " + pedido2Fernando);
		System.out.println(">>> Gravando Cliente 1: " + fernando);
		
	}
}
