package br.com.fiap.programa;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.fiap.entity.Clientes;
import br.com.fiap.entity.Pedidos;
import br.com.fiap.helper.PersistenciaHelper;

public class PersistenciaHelperTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Atividade02");

		EntityManager em = emf.createEntityManager();
		incluirCliente(em);
		em = emf.createEntityManager();
		listarClientes(em);
		buscarCliente(em, 1);
		em.close();
		System.exit(1);
	}

	private static void incluirCliente(EntityManager em) {
		PersistenciaHelper dao = new PersistenciaHelper(em);
		Clientes cliente = new Clientes();
		cliente.setEmail("lramosouza@outlook.com");
		cliente.setNome("Leandro Ramo de Souza");

		Pedidos pedido = new Pedidos();
		pedido.setCliente(cliente);
		pedido.setData(new Date());
		pedido.setDescricao("Galaxy S8+");
		pedido.setValor(4000);

		cliente.getPedidos().add(pedido);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);

		Pedidos pedido2 = new Pedidos();
		pedido2.setCliente(cliente);
		pedido2.setData(cal.getTime());
		pedido2.setDescricao("Notebook core i9");
		pedido2.setValor(12000);

		cliente.getPedidos().add(pedido2);

		try {
			dao.salvar(cliente);
			System.out.println("Salvo com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void listarClientes(EntityManager em) {
		PersistenciaHelper dao = new PersistenciaHelper(em);
		List<Clientes> clientes = dao.listarClientes();
		for (Clientes cliente : clientes) {
			System.out.println(cliente.getNome() + ": " + cliente.getEmail());
		}
	}

	private static void buscarCliente(EntityManager em, Integer idCliente) {
		PersistenciaHelper dao = new PersistenciaHelper(em);
		Clientes f = dao.buscarCliente(idCliente);
		System.out.println(f.getNome() + ": " + f.getEmail());
	}

}